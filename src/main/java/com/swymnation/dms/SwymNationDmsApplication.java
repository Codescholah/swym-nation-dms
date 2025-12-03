package com.swymnation.dms;

import com.swymnation.dms.controller.dto.RegistrationForm;
import com.swymnation.dms.model.*;
import com.swymnation.dms.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class SwymNationDmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwymNationDmsApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(
            RegistrationService registrationService,
            AttendanceManager attendanceManager,
            PaymentService paymentService,
            NotificationScheduler notificationScheduler,
            ReportAggregator reportAggregator,
            ReportGeneratorService reportGenService) {
        return args -> {
            System.out.println("--- SYSTEM STARTUP SIMULATION ---");

            // 1. Register Client
            System.out.println("\n[FR-01] Registering New Client...");
            RegistrationForm form = new RegistrationForm();
            form.setFirstName("Jane"); form.setLastName("Doe"); 
            form.setEmail("jane@example.com"); form.setPhoneNumber("555-0199"); form.setAddress("123 Swim Lane");
            Client newClient = registrationService.registerClient(form);
            System.out.println("Client Registered: ID " + newClient.getId());

            // 2. Process Payment
            System.out.println("\n[FR-03] Processing Payment...");
            String receipt = paymentService.processPayment(newClient, 150.00);
            System.out.println(receipt);

            // 3. Reports (NEW)
            System.out.println("\n[FR-05] Generating Financial Report...");
            User admin = new User("admin_user", "admin@swym.com", UserRole.ADMINISTRATOR);
            
            ReportCriteria criteria = new ReportCriteria(
                LocalDate.now().minusDays(30), LocalDate.now(), null, null, "FINANCIAL"
            );
            
            ReportData data = reportAggregator.generateReportData(criteria);
            String reportOutput = reportGenService.exportReport(data, admin, ReportFormat.PDF);
            System.out.println(reportOutput);

            // 4. Notification
            System.out.println("\n[FR-06] Scheduling Notification...");
            User notifUser = new User("jane_doe", "jane@example.com", UserRole.CLIENT);
            notifUser.setNotificationPreferences(Set.of("EMAIL"));
            Notification n = new Notification("ALERT", notifUser, "Class Canceled", "Pool maintenance required.", "EMAIL");
            notificationScheduler.scheduleNotification(n, 2);

            System.out.println("\n--- SIMULATION COMPLETE ---");
        };
    }
}