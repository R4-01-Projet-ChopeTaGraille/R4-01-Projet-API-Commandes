package fr.univamu.r401.commandes.adapter.persistence;

import fr.univamu.r401.commandes.entity.Order;
import fr.univamu.r401.commandes.entity.OrderLine;
import fr.univamu.r401.commandes.usecase.OrderRepositoryGateway;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation JDBC de OrderRepositoryGateway.
 * Accès aux tables commande et ligne_commande via la datasource GlassFish.
 */
@ApplicationScoped
public class OrderJdbcGateway implements OrderRepositoryGateway {

    @Resource(lookup = "jdbc/commandesDS")
    private DataSource dataSource;

    @Override
    public Order save(Order order) {
        String sqlOrder = "INSERT INTO commande (abonne_id, date_commande, adresse_livraison, date_livraison, prix_total) VALUES (?, ?, ?, ?, ?)";
        String sqlLine  = "INSERT INTO ligne_commande (commande_id, menu_id, menu_nom, quantite, prix_unitaire, prix_ligne) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection()) {
            // Insérer la commande et récupérer l'id généré
            try (PreparedStatement ps = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, order.getSubscriberId());
                ps.setObject(2, order.getOrderDate());
                ps.setString(3, order.getDeliveryAddress());
                ps.setObject(4, order.getDeliveryDate());
                ps.setDouble(5, order.getTotalPrice());
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        order.setId(keys.getInt(1));
                    }
                }
            }

            // Insérer chaque ligne
            try (PreparedStatement ps = conn.prepareStatement(sqlLine)) {
                for (OrderLine line : order.getLines()) {
                    ps.setInt(1, order.getId());
                    ps.setInt(2, line.getMenuId());
                    ps.setString(3, line.getMenuName());
                    ps.setInt(4, line.getQuantity());
                    ps.setDouble(5, line.getUnitPrice());
                    ps.setDouble(6, line.getLinePrice());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la commande", e);
        }

        return order;
    }

    @Override
    public Optional<Order> findById(int id) {
        String sqlOrder = "SELECT * FROM commande WHERE id = ?";
        String sqlLines = "SELECT * FROM ligne_commande WHERE commande_id = ?";

        try (Connection conn = dataSource.getConnection()) {
            Order order = null;

            try (PreparedStatement ps = conn.prepareStatement(sqlOrder)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        order = mapOrder(rs);
                    }
                }
            }

            if (order == null) return Optional.empty();

            try (PreparedStatement ps = conn.prepareStatement(sqlLines)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        order.addLine(mapOrderLine(rs));
                    }
                }
            }

            return Optional.of(order);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la commande id=" + id, e);
        }
    }

    @Override
    public List<Order> findAll() {
        return findByQuery("SELECT * FROM commande", null);
    }

    @Override
    public List<Order> findBySubscriberId(int subscriberId) {
        return findByQuery("SELECT * FROM commande WHERE abonne_id = ?", subscriberId);
    }

    @Override
    public Order update(Order order) {
        String sql = "UPDATE commande SET adresse_livraison = ?, date_livraison = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, order.getDeliveryAddress());
            ps.setObject(2, order.getDeliveryDate());
            ps.setInt(3, order.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la commande id=" + order.getId(), e);
        }

        return order;
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM commande WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la commande id=" + id, e);
        }
    }

    /** Exécute une requête SELECT sur commande avec un paramètre entier optionnel. */
    private List<Order> findByQuery(String sql, Integer param) {
        String sqlLines = "SELECT * FROM ligne_commande WHERE commande_id = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (param != null) ps.setInt(1, param);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }

            for (Order order : orders) {
                try (PreparedStatement ps2 = conn.prepareStatement(sqlLines)) {
                    ps2.setInt(1, order.getId());
                    try (ResultSet rs2 = ps2.executeQuery()) {
                        while (rs2.next()) {
                            order.addLine(mapOrderLine(rs2));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commandes", e);
        }

        return orders;
    }

    /** Construit un Order depuis un ResultSet positionné sur une ligne de commande. */
    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setSubscriberId(rs.getInt("abonne_id"));
        order.setOrderDate(rs.getTimestamp("date_commande").toLocalDateTime());
        order.setDeliveryAddress(rs.getString("adresse_livraison"));
        order.setDeliveryDate(rs.getDate("date_livraison").toLocalDate());
        order.setTotalPrice(rs.getDouble("prix_total"));
        return order;
    }

    /** Construit un OrderLine depuis un ResultSet positionné sur une ligne de ligne_commande. */
    private OrderLine mapOrderLine(ResultSet rs) throws SQLException {
        OrderLine line = new OrderLine();
        line.setId(rs.getInt("id"));
        line.setMenuId(rs.getInt("menu_id"));
        line.setMenuName(rs.getString("menu_nom"));
        line.setQuantity(rs.getInt("quantite"));
        line.setUnitPrice(rs.getDouble("prix_unitaire"));
        line.setLinePrice(rs.getDouble("prix_ligne"));
        return line;
    }
}
