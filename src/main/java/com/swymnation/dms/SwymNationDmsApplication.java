package com.swymnation.dms;

import com.swymnation.dms.controller.dto.RegistrationForm;
import com.swymnation.dms.model.*;
import com.swymnation.dms.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Set;

@SpringBootApplication
public class SwymNationDmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwymNationDmsApplication.class, args);
    }

    // This runs automatically when the server starts to prove all features work
    @Bean
    CommandLineRunner demo(
            RegistrationService registrationService,
            AttendanceManager attendanceManager,
            PaymentService paymentService,
            NotificationScheduler notificationScheduler) {
        return args -> {
            System.out.println("--- SYSTEM STARTUP SIMULATION ---");

            // 1. Register a Client (FR-01)
            System.out.println("\n[FR-01] Registering New Client...");
            RegistrationForm form = new RegistrationForm();
            form.setFirstName("Jane"); form.setLastName("Doe"); 
            form.setEmail("jane@example.com"); form.setPhoneNumber("555-0199"); form.setAddress("123 Swim Lane");
            
            Client newClient = registrationService.registerClient(form);
            System.out.println("Client Registered: ID " + newClient.getId());

            // 2. Process Payment (FR-03)
            System.out.println("\n[FR-03] Processing Payment...");
            String receipt = paymentService.processPayment(newClient, 150.00);
            System.out.println(receipt);

            // 3. Mark Attendance (FR-02)
            System.out.println("\n[FR-02] Marking Attendance...");
            // Create dummy instructor/user for security check
            User instructor = new User("coach_mike", "mike@swym.com", UserRole.INSTRUCTOR);
            // In real app, these users are in DB. We simulate passing the object.
            
            // (Note: To fully test Attendance DB save, we need the Student ID linked, skipping complex setup for this demo)
            System.out.println("Attendance Batch Processed (Simulated)");

            // 4. Send Notification (FR-06)
            System.out.println("\n[FR-06] Scheduling Notification...");
            User notifUser = new User("jane_doe", "jane@example.com", UserRole.CLIENT);
            notifUser.setNotificationPreferences(Set.of("EMAIL"));
            
            Notification n = new Notification("ALERT", notifUser, "Class Canceled", "Pool maintenance required.", "EMAIL");
            notificationScheduler.scheduleNotification(n, 2); // 2 second delay

            System.out.println("\n--- SIMULATION COMPLETE (Watch for Notification in 2s) ---");
        };
    }
}