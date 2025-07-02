package com.example.springlra;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping("/pay")
    @LRA(value = LRA.Type.MANDATORY)
    public ResponseEntity<String> pay(@RequestHeader(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        boolean success = false;
        if (success) {
            return ResponseEntity.ok("Paid");
        }
        throw new RuntimeException("Payment failed");
    }

    @PutMapping("/pay")
    @Compensate
    public ResponseEntity<String> compensate(@RequestHeader(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        return ResponseEntity.ok("Payment compensated!");
    }
}
