package com.example;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;

@Path("/order")
public class OrderResource {

    @POST
    @LRA(value = LRA.Type.REQUIRED)
    @Path("/create")
    public Uni<Response> createOrder(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        return callPaymentService(lraId)
            .onItem().transform(result -> Response.ok("Order & Payment OK").build());
    }

    @PUT
    @Path("/create")
    @Compensate
    public Uni<Response> compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        return revertOrder(lraId)
            .onItem().transform(done -> Response.ok("Order compensated!").build());
    }

    private Uni<Boolean> callPaymentService(String lraId) {
        return Uni.createFrom().item(true);
    }

    private Uni<Boolean> revertOrder(String lraId) {
        return Uni.createFrom().item(true);
    }
}

