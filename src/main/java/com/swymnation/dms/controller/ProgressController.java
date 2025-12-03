package com.swymnation.dms.controller;

import com.swymnation.dms.model.*;
import com.swymnation.dms.service.ProgressManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressManager progressManager;

    @Autowired
    public ProgressController(ProgressManager manager) { this.progressManager = manager; }

    @PostMapping("/update")
    public ResponseEntity<?> updateProgress(@RequestParam Long studentId, 
                                            @RequestParam Integer level,
                                            @RequestParam String notes,
                                            @RequestHeader("X-Role") String role) {
        try {
            // Mock Auth
            User instructor = new User("MockInst", "i@swym.com", UserRole.valueOf(role));
            instructor.setId(55L);

            ProgressUpdate update = progressManager.updateProgress(instructor, studentId, level, notes);
            return ResponseEntity.ok(update);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update Failed: " + e.getMessage());
        }
    }
}