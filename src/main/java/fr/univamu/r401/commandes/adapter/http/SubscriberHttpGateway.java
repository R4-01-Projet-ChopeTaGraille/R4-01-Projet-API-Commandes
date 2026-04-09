package fr.univamu.r401.commandes.adapter.http;

import fr.univamu.r401.commandes.adapter.http.response.SubscriberApiResponse;
import fr.univamu.r401.commandes.usecase.SubscriberGateway;
import fr.univamu.r401.commandes.usecase.io.SubscriberSnapshot;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

/**
 * Implémentation de SubscriberGateway via un appel HTTP à l'API Plats & Utilisateurs.
 * Pointe vers le mock JSON-Server (port 3003) en développement.
 */
@ApplicationScoped
public class SubscriberHttpGateway implements SubscriberGateway {

    private static final String BASE_URL = "http://localhost:3003";

    @Override
    public Optional<SubscriberSnapshot> findSubscriberById(int subscriberId) {
        Client client = ClientBuilder.newClient();
        try {
            Response response = client
                    .target(BASE_URL + "/utilisateurs/" + subscriberId)
                    .request()
                    .get();

            if (response.getStatus() == 200) {
                SubscriberApiResponse body = response.readEntity(SubscriberApiResponse.class);
                return Optional.of(new SubscriberSnapshot(body.id, body.nom, body.prenom));
            }
            return Optional.empty();
        } finally {
            client.close();
        }
    }
}
