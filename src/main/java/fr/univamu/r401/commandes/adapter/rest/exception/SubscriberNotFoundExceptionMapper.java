package fr.univamu.r401.commandes.adapter.rest.exception;

import fr.univamu.r401.commandes.usecase.exception.SubscriberNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/** Traduit SubscriberNotFoundException en HTTP 404. */
@Provider
public class SubscriberNotFoundExceptionMapper implements ExceptionMapper<SubscriberNotFoundException> {

    @Override
    public Response toResponse(SubscriberNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity("{\"error\":\"" + e.getMessage() + "\"}")
                .build();
    }
}
