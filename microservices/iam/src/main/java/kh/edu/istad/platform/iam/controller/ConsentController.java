package kh.edu.istad.platform.iam.controller;

import kh.edu.istad.platform.iam.feature.consent.dto.ConsentResponse;
import kh.edu.istad.platform.iam.service.ConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/consents")
@RequiredArgsConstructor
public class ConsentController {

    private final ConsentService consentService;

    // View Consents for current logged-in user
    @GetMapping("/{clientId}")
    public ResponseEntity<ConsentResponse> getConsent(@PathVariable String clientId, Principal principal){
        return ResponseEntity.ok(consentService.getConsent(clientId, principal.getName()));
    }

    // Revoke consent, disconnect application
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> revokeConsent(@PathVariable String clientId, Principal principal){

        consentService.revokeConsent(clientId, principal.getName());

        return ResponseEntity.noContent().build();
    }
}
