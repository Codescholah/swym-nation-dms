package com.swymnation.dms.service;

import com.swymnation.dms.model.Client;
import org.springframework.stereotype.Service;

public interface PaymentGatewayProxy {
    String initiatePayment(Client client);
}

// Temporary Mock Implementation so the app runs
@Service
class MockPaymentGateway implements PaymentGatewayProxy {
    @Override
    public String initiatePayment(Client client) {
        // Step 1: Log the start
        System.out.print("MOCK GATEWAY: Processing payment for " + client.getEmail() + " ... ");
        
        // Step 2: Generate the ID
        String transactionId = "TXN-" + System.currentTimeMillis();
        
        // Step 3: Log the completion (This is the "progress" you want to see)
        System.out.println("[SUCCESS] Payment Complete. ID: " + transactionId);
        
        return transactionId;
    }
}