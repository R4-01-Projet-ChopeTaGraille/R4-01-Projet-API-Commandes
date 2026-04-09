package fr.univamu.r401.commandes.adapter.http;

import fr.univamu.r401.commandes.adapter.http.response.MenuApiResponse;
import fr.univamu.r401.commandes.usecase.MenuGateway;
import fr.univamu.r401.commandes.usecase.io.MenuSnapshot;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

/**
 * Implémentation de MenuGateway via un appel HTTP à l'API Menus.
 * Pointe vers le mock JSON-Server (port 3004) en développement.
 */
@ApplicationScoped
public class MenuHttpGateway implements MenuGateway {

    private static final String BASE_URL = "http://localhost:3004";

    @Override
    public Optional<MenuSnapshot> findMenuById(int menuId) {
        Client client = ClientBuilder.newClient();
        try {
            Response response = client
                    .target(BASE_URL + "/menus/" + menuId)
                    .request()
                    .get();

            if (response.getStatus() == 200) {
                MenuApiResponse body = response.readEntity(MenuApiResponse.class);
                return Optional.of(new MenuSnapshot(body.id, body.nom, body.prixTotal));
            }
            return Optional.empty();
        } finally {
            client.close();
        }
    }
}
