package fr.univamu.r401.commandes.usecase.io;

import java.time.LocalDate;

/** Données d'entrée pour la mise à jour d'une commande (adresse et/ou date de livraison). */
public class UpdateOrderInput {

    /** Nouvelle adresse de livraison. */
    public String deliveryAddress;

    /** Nouvelle date de livraison, doit être dans le futur. */
    public LocalDate deliveryDate;

    public UpdateOrderInput() {}
}
