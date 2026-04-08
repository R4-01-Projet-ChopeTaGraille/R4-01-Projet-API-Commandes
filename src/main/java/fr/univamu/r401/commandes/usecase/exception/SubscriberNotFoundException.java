package fr.univamu.r401.commandes.usecase.exception;

/** Levée quand l'abonné référencé dans une commande n'existe pas dans l'API Plats & Utilisateurs. */
public class SubscriberNotFoundException extends RuntimeException {

    public SubscriberNotFoundException(int subscriberId) {
        super("Abonné introuvable : id=" + subscriberId);
    }
}
