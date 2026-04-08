package fr.univamu.r401.commandes.usecase;

import fr.univamu.r401.commandes.usecase.io.MenuSnapshot;

import java.util.Optional;

/**
 * Passerelle vers l'API Menus.
 * Implémentée par MenuHttpGateway dans la couche adapter.
 */
public interface MenuGateway {

    /** Récupère les données d'un menu par son identifiant. */
    Optional<MenuSnapshot> findMenuById(int menuId);
}
