package fr.univamu.r401.commandes.usecase;

import fr.univamu.r401.commandes.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * Passerelle vers le système de persistance des commandes.
 * Implémentée par OrderJdbcGateway dans la couche adapter.
 */
public interface OrderRepositoryGateway {

    /** Persiste une nouvelle commande et retourne la commande avec son id généré. */
    Order save(Order order);

    /** Recherche une commande par son identifiant. */
    Optional<Order> findById(int id);

    /** Retourne toutes les commandes. */
    List<Order> findAll();

    /** Retourne les commandes d'un abonné donné. */
    List<Order> findBySubscriberId(int subscriberId);

    /** Met à jour une commande existante. */
    Order update(Order order);

    /** Supprime une commande par son identifiant. */
    void delete(int id);
}
