package org.acme.ui;

import java.util.List;

import org.acme.core.service.GreetingService;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    GreetingService greetingService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    public String hello(String name) {
        return greetingService.greeting(name).await().indefinitely();
    }

    @GET
    @Path("/greetings/{count}/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String greetings(int count, String name) {
        return greetingService.greetings(count, name)
                .collect().asList()
                .await().indefinitely()
                .toString();
    }

    @POST
    @Path("/greetings")
    @Produces(MediaType.TEXT_PLAIN)
    public Multi<String> greetings(List<String> names) {
        return greetingService.greetings(names);
    }
}
