package fr.univamu.r401.commandes.usecase.io;

import java.time.LocalDate;
import java.util.List;

/** Données d'entrée pour la création d'une commande. */
public class CreateOrderInput {

    /** Identifiant de l'abonné. */
    public int subscriberId;

    /** Adresse de livraison. */
    public String deliveryAddress;

    /** Date de livraison souhaitée, doit être dans le futur. */
    public LocalDate deliveryDate;

    /** Lignes de la commande, doit contenir au moins un élément. */
    public List<OrderLineInput> lines;

    public CreateOrderInput() {}
}
