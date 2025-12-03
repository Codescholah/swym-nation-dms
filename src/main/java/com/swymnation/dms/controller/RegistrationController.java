package com.swymnation.dms.controller;

import com.swymnation.dms.controller.dto.RegistrationForm;
import com.swymnation.dms.model.Client;
import com.swymnation.dms.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // This tells Spring: "I am a Web Endpoint"
@RequestMapping("/api/clients") // This sets the base URL
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    // LISTENS FOR: POST requests to http://localhost:8080/api/clients/register
    @PostMapping("/register")
    public ResponseEntity<?> registerNewClient(@Valid @RequestBody RegistrationForm form) {
        try {
            // 1. The @Valid annotation automatically triggers your @NotBlank checks
            // 2. If valid, we call the service
            Client newClient = registrationService.registerClient(form);
            
            return ResponseEntity.ok()
                .body("SUCCESS: Registered Client ID " + newClient.getId());
                
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }
}