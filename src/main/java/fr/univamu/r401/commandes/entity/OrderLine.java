package fr.univamu.r401.commandes.entity;

/**
 * Ligne de commande : un menu commandé en une certaine quantité.
 * Le nom et le prix du menu sont snapshotés à la création de la commande.
 */
public class OrderLine {

    /** Identifiant unique généré par la base de données. */
    private int id;

    /** Identifiant du menu dans l'API Menus. */
    private int menuId;

    /** Nom du menu au moment de la commande (snapshot). */
    private String menuName;

    /** Quantité commandée, doit être >= 1. */
    private int quantity;

    /** Prix unitaire du menu au moment de la commande (snapshot). */
    private double unitPrice;

    /** Prix de la ligne = unitPrice × quantity, calculé côté serveur. */
    private double linePrice;

    public OrderLine() {}

    /** Calcule et affecte linePrice = unitPrice × quantity. */
    public void computeLinePrice() {
        this.linePrice = this.unitPrice * this.quantity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getLinePrice() { return linePrice; }
    public void setLinePrice(double linePrice) { this.linePrice = linePrice; }
}
