package fr.univamu.r401.commandes;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Point d'entrée de l'application JAX-RS.
 * Toutes les routes REST seront disponibles sous /commandes/api/...
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
}
