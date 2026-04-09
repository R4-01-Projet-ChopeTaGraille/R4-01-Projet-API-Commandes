package fr.univamu.r401.commandes.adapter.rest.exception;

import fr.univamu.r401.commandes.usecase.exception.MenuNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/** Traduit MenuNotFoundException en HTTP 404. */
@Provider
public class MenuNotFoundExceptionMapper implements ExceptionMapper<MenuNotFoundException> {

    @Override
    public Response toResponse(MenuNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity("{\"error\":\"" + e.getMessage() + "\"}")
                .build();
    }
}
