package org.acme.ui;

import java.util.List;
import java.util.Optional;

import org.acme.core.domain.QuarkusUser;
import org.acme.core.service.UserService;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.ToString;

@Path("/users")
@RegisterRestClient
public class UserResource {

    @Inject
    UserService userService;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUser() {
        return userService.getUserInfo();
    }

    @GET
    @Produces("application/json")
    @Path("/all")
    public Iterable<QuarkusUser> findAll() {
        return userService.findAll();
    }

    @DELETE
    @Path("{id}")
    public void delete(long id) {
        userService.delete(id);
    }

    @POST
    @Path("/create")
    public QuarkusUser create(QuarkusUser user) {
        return userService.create(user.getName(), user.getEmail());
    }

    @ToString
    public static class UserRequest{
        public String name;
        public String email;
    }

    @PUT
    @Path("/id/{id}/email/{email}")
    @Produces("application/json")
    public QuarkusUser changeemail(Long id, String email) {
        Optional<QuarkusUser> optional = userService.findById(id);
        if (optional.isPresent()) {
            QuarkusUser user = optional.get();
            user.setEmail(email);
            return userService.create(user.getName(), user.getEmail());
        }

        throw new IllegalArgumentException("No QuarkusUser with id " + id + " exists");
    }

    @GET
    @Path("/email/{email}")
    @Produces("application/json")
    public List<QuarkusUser> findByEmail(String email) {
        return userService.findByEmail(email);
    }
}
