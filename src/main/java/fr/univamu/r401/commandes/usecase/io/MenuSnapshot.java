package fr.univamu.r401.commandes.usecase.io;

/** Snapshot des données d'un menu au moment de la commande, retourné par MenuGateway. */
public class MenuSnapshot {

    public int id;
    public String name;
    public double totalPrice;

    public MenuSnapshot() {}

    public MenuSnapshot(int id, String name, double totalPrice) {
        this.id = id;
        this.name = name;
        this.totalPrice = totalPrice;
    }
}
