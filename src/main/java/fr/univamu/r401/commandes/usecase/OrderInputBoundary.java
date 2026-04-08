package fr.univamu.r401.commandes.usecase;

import fr.univamu.r401.commandes.usecase.io.CreateOrderInput;
import fr.univamu.r401.commandes.usecase.io.OrderOutputData;
import fr.univamu.r401.commandes.usecase.io.UpdateOrderInput;

import java.util.List;

/**
 * Frontière d'entrée du cas d'utilisation Commandes.
 * Appelée par OrderController, implémentée par OrderInteractor.
 */
public interface OrderInputBoundary {

    /** Crée une nouvelle commande. */
    OrderOutputData createOrder(CreateOrderInput input);

    /** Retourne une commande par son identifiant. */
    OrderOutputData getOrderById(int id);

    /**
     * Retourne toutes les commandes, filtrées par abonné si subscriberId est fourni.
     * @param subscriberId l'id de l'abonné pour filtrer, ou null pour tout retourner
     */
    List<OrderOutputData> listOrders(Integer subscriberId);

    /** Met à jour l'adresse ou la date de livraison d'une commande. */
    OrderOutputData updateOrder(int id, UpdateOrderInput input);

    /** Annule une commande. */
    void cancelOrder(int id);
}
