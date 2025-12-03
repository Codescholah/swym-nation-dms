package com.swymnation.dms.controller;

import com.swymnation.dms.model.*;
import com.swymnation.dms.service.AttendanceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceManager attendanceManager;

    @Autowired
    public AttendanceController(AttendanceManager manager) { this.attendanceManager = manager; }

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody List<Attendance> records, 
                                            @RequestHeader("X-Role") String role) {
        try {
            // Mock Authentication: Create a dummy user based on the Header
            User instructor = new User("MockInstructor", "inst@swym.com", UserRole.valueOf(role));
            instructor.setId(99L); 

            List<Attendance> saved = attendanceManager.recordBatchAttendance(instructor, records);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}