package fr.univamu.r401.commandes.usecase.io;

/** Données de sortie représentant une ligne de commande. */
public class OrderLineOutputData {

    public int menuId;
    public String menuName;
    public int quantity;
    public double unitPrice;
    public double linePrice;

    public OrderLineOutputData() {}
}
