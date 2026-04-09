package fr.univamu.r401.commandes.adapter.rest;

import fr.univamu.r401.commandes.usecase.OrderInputBoundary;
import fr.univamu.r401.commandes.usecase.io.CreateOrderInput;
import fr.univamu.r401.commandes.usecase.io.OrderOutputData;
import fr.univamu.r401.commandes.usecase.io.UpdateOrderInput;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

/**
 * Controller JAX-RS exposant les endpoints REST de l'API Commandes.
 * Délègue toute la logique métier à OrderInputBoundary.
 */
@Path("/commandes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    @Inject
    private OrderInputBoundary useCase;

    @GET
    public Response getAll(@QueryParam("abonneId") Integer subscriberId) {
        List<OrderOutputData> list = useCase.listOrders(subscriberId);
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        OrderOutputData result = useCase.getOrderById(id);
        return Response.ok(result).build();
    }

    @POST
    public Response create(CreateOrderInput input) {
        OrderOutputData result = useCase.createOrder(input);
        return Response.created(URI.create("/commandes/" + result.id))
                .entity(result)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, UpdateOrderInput input) {
        OrderOutputData result = useCase.updateOrder(id, input);
        return Response.ok(result).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        useCase.cancelOrder(id);
        return Response.noContent().build();
    }
}
