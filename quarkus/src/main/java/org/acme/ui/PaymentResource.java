import org.eclipse.microprofile.lra.annotation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;

@Path("/payment")
public class PaymentResource {

    @POST
    @LRA(value = LRA.Type.REQUIRED)
    @Path("/reserve")
    @Consumes("application/json")
    public Uni<Response> reservePayment(PaymentRequest req, @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        return processReserve(req)
            .onItem().transform(success -> {
                if (success) return Response.ok("Reserved").build();
                else throw new WebApplicationException("Fail", 500);
            });
    }

    @PUT
    @Path("/reserve")
    @Compensate
    public Uni<Response> compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        return revertReserve(lraId)
            .onItem().transform(done -> Response.ok("Compensated!").build());
    }

    private Uni<Boolean> processReserve(PaymentRequest req) {
        return Uni.createFrom().item(true);
    }

    private Uni<Boolean> revertReserve(String lraId) {
        return Uni.createFrom().item(true);
    }
}

