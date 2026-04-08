package fr.univamu.r401.commandes.usecase.exception;

/** Levée quand un menu référencé dans une commande n'existe pas dans l'API Menus. */
public class MenuNotFoundException extends RuntimeException {

    public MenuNotFoundException(int menuId) {
        super("Menu introuvable : id=" + menuId);
    }
}
