package com.swymnation.dms.controller;

import com.swymnation.dms.model.Client;
import com.swymnation.dms.service.PaymentService;
import com.swymnation.dms.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final ClientRepository clientRepository;

    @Autowired
    public PaymentController(PaymentService service, ClientRepository repo) {
        this.paymentService = service;
        this.clientRepository = repo;
    }

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestParam Long clientId, @RequestParam Double amount) {
        try {
            Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
                
            String receipt = paymentService.processPayment(client, amount);
            return ResponseEntity.ok(receipt);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Payment Failed: " + e.getMessage());
        }
    }
}