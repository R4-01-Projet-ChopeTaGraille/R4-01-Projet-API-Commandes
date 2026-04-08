package fr.univamu.r401.commandes.usecase.exception;

/** Levée quand une commande demandée n'existe pas en base. */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(int id) {
        super("Commande introuvable : id=" + id);
    }
}
