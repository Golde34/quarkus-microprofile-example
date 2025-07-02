package com.example.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey="payment")
@Path("/payment")
public interface PaymentClient {
    @POST
    @Path("/pay")
    @LRA(value = LRA.Type.MANDATORY)
    Response pay(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId);
}
