package fr.univamu.r401.commandes.usecase;

import fr.univamu.r401.commandes.usecase.io.SubscriberSnapshot;

import java.util.Optional;

/**
 * Passerelle vers l'API Plats & Utilisateurs.
 * Implémentée par SubscriberHttpGateway dans la couche adapter.
 */
public interface SubscriberGateway {

    /** Vérifie l'existence d'un abonné et retourne ses données. */
    Optional<SubscriberSnapshot> findSubscriberById(int subscriberId);
}
