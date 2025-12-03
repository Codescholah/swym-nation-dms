package com.swymnation.dms.controller;

import com.swymnation.dms.model.*;
import com.swymnation.dms.service.NotificationScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationScheduler scheduler;

    @Autowired
    public NotificationController(NotificationScheduler scheduler) {
        this.scheduler = scheduler;
    }

    // LISTENS FOR: POST /api/notifications/trigger
    @PostMapping("/trigger")
    public ResponseEntity<?> manualTrigger(@RequestParam String subject, 
                                           @RequestParam String body,
                                           @RequestHeader("X-Role") String role) {
        try {
            // 1. Security Check: Only Admins can manually trigger (AC 4)
            if (!"ADMINISTRATOR".equals(role)) {
                return ResponseEntity.status(403).body("Access Denied: Only Admins can manual trigger.");
            }

            // 2. Mock a Target User (In real app, you'd fetch from DB)
            User target = new User("TargetClient", "client@test.com", UserRole.CLIENT);
            target.setNotificationPreferences(Set.of("EMAIL")); // Ensure they opted-in

            // 3. Create & Schedule
            Notification notification = new Notification(
                "MANUAL_ALERT", target, subject, body, "EMAIL"
            );

            // 0 second delay for immediate testing
            scheduler.scheduleNotification(notification, 0);

            return ResponseEntity.ok("SUCCESS: Notification queued for delivery.");

        } catch (IllegalArgumentException e) {
            // Catches "Privacy Violation" (Credit Card info)
            return ResponseEntity.badRequest().body("Security Block: " + e.getMessage());
        }
    }
}