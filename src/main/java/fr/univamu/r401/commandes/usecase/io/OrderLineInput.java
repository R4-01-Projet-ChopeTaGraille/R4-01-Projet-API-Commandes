package fr.univamu.r401.commandes.usecase.io;

/** Données d'entrée pour une ligne lors de la création d'une commande. */
public class OrderLineInput {

    /** Identifiant du menu dans l'API Menus. */
    public int menuId;

    /** Quantité souhaitée, doit être >= 1. */
    public int quantity;

    public OrderLineInput() {}
}
