package ui;

import core.domain.Fruit;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import java.net.URI;

@Path("fruits")
public class FruitResource {

    private final Pool client;

    public FruitResource(Pool client) {
        this.client = client;
    }

    @GET
    public Multi<Fruit> get() {
        return Fruit.findAll(client);
    }

    @GET
    @Path("{id}")
    public Uni<Response> getSingle(Long id) {
        return Fruit.findById(client, id)
                .onItem().transform(fruit -> fruit != null ? Response.ok(fruit) : Response.status(Status.NOT_FOUND))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @POST
    public Uni<Response> create(Fruit fruit) {
        return fruit.save(client)
                .onItem().transform(id -> URI.create("/fruits/" + id))
                .onItem().transform(uri -> Response.created(uri).build());
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(Long id, Fruit fruit) {
        return fruit.update(client)
                .onItem().transform(updated -> updated ? Response.Status.OK : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return Fruit.delete(client, id)
                .onItem().transform(deleted -> deleted ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }
}
