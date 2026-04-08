package fr.univamu.r401.commandes.usecase.io;

/** Snapshot des données d'un abonné, retourné par SubscriberGateway. */
public class SubscriberSnapshot {

    public int id;
    public String nom;
    public String prenom;

    public SubscriberSnapshot() {}

    public SubscriberSnapshot(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
}
