package fr.univamu.r401.commandes.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Commande passée par un abonné, regroupant plusieurs lignes de commande.
 * Le prix total et la date de commande sont calculés côté serveur.
 */
public class Order {

    /** Identifiant unique généré par la base de données. */
    private int id;

    /** Identifiant de l'abonné dans l'API Plats & Utilisateurs. */
    private int subscriberId;

    /** Date et heure de validation, horodatée côté serveur. */
    private LocalDateTime orderDate;

    /** Adresse de livraison. */
    private String deliveryAddress;

    /** Date de livraison souhaitée, doit être dans le futur. */
    private LocalDate deliveryDate;

    /** Prix total = somme des linePrice, calculé côté serveur. */
    private double totalPrice;

    /** Lignes de commande associées. */
    private List<OrderLine> lines = new ArrayList<>();

    public Order() {}

    /**
     * Ajoute une ligne à la commande.
     * @param line la ligne à ajouter
     */
    public void addLine(OrderLine line) {
        lines.add(line);
    }

    /** Calcule et affecte totalPrice = somme des linePrice de toutes les lignes. */
    public void computeTotalPrice() {
        this.totalPrice = lines.stream()
                .mapToDouble(OrderLine::getLinePrice)
                .sum();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSubscriberId() { return subscriberId; }
    public void setSubscriberId(int subscriberId) { this.subscriberId = subscriberId; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public List<OrderLine> getLines() { return lines; }
    public void setLines(List<OrderLine> lines) { this.lines = lines; }
}
