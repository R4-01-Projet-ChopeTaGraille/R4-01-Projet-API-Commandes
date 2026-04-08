package fr.univamu.r401.commandes.usecase.exception;

/** Levée quand les données d'une commande sont invalides. */
public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String message) {
        super(message);
    }
}
