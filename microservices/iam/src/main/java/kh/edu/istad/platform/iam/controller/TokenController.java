package kh.edu.istad.platform.iam.controller;

import kh.edu.istad.platform.iam.feature.authorization.dto.TokenIntrospectResponse;
import kh.edu.istad.platform.iam.service.TokenManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/token")
public class TokenController {

    private final TokenManagementService tokenManagementService;
    public TokenController(TokenManagementService tokenManagementService) {
        this.tokenManagementService = tokenManagementService;
    }

    @RequestMapping("/revoke")
    public ResponseEntity<Void> revokeToken(@RequestParam String token) {

        tokenManagementService.revokeToken(token);

        return ResponseEntity.noContent().build();

    }
    @GetMapping("/introspect")
    public ResponseEntity<TokenIntrospectResponse> introspectToken(@RequestParam String token) {
        return ResponseEntity.ok(tokenManagementService.introspectToken(token));
    }
}
