package com.example;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;

import com.example.client.PaymentClient;

@Path("/order")
public class OrderResource {

    private static final Logger LOG = Logger.getLogger(OrderResource.class);

    @Inject
    @RestClient
    PaymentClient paymentClient;

    @POST
    @LRA(value = LRA.Type.REQUIRED)
    @Path("/create")
    public Uni<Response> createOrder(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        LOG.info("Creating order and calling payment service");
        return callPaymentService(lraId)
            .onItem().transform(result -> Response.ok("Order & Payment OK").build());
    }

    @PUT
    @Path("/create")
    @Compensate
    public Uni<Response> compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        LOG.info("Compensating order");
        return revertOrder(lraId)
            .onItem().transform(done -> Response.ok("Order compensated!").build());
    }

    private Uni<Boolean> callPaymentService(String lraId) {
        return Uni.createFrom().item(() -> paymentClient.pay(lraId))
                .onItem().invoke(resp -> LOG.infof("Payment service responded %d", resp.getStatus()))
                .onItem().transform(resp -> resp.getStatus() == 200);
    }

    private Uni<Boolean> revertOrder(String lraId) {
        return Uni.createFrom().item(true);
    }
}

