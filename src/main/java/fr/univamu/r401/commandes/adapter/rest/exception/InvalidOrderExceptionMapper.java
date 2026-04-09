package fr.univamu.r401.commandes.adapter.rest.exception;

import fr.univamu.r401.commandes.usecase.exception.InvalidOrderException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/** Traduit InvalidOrderException en HTTP 400. */
@Provider
public class InvalidOrderExceptionMapper implements ExceptionMapper<InvalidOrderException> {

    @Override
    public Response toResponse(InvalidOrderException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity("{\"error\":\"" + e.getMessage() + "\"}")
                .build();
    }
}
