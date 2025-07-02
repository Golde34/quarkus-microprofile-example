package org.acme.ui;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/payment")
public class PaymentResource {

    private static final Logger LOG = Logger.getLogger(PaymentResource.class);

    @POST
    @LRA(value = LRA.Type.MANDATORY)
    @Path("/pay")
    public Uni<Response> pay(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        LOG.info("Processing payment");
        boolean isSuccess = false;
        if (isSuccess) {
            return Uni.createFrom().item(Response.ok("Paid").build());
        } else {
            return Uni.createFrom().failure(new RuntimeException("Payment failed"));
        }
    }

    @PUT
    @Path("/pay")
    @Compensate
    public Uni<Response> compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        LOG.info("Compensating payment");
        return revertPayment(lraId)
            .onItem().transform(done -> Response.ok("Payment compensated!").build());
    }

    private Uni<Boolean> revertPayment(String lraId) {
        return Uni.createFrom().item(true);
    }
}

