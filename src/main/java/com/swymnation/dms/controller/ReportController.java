package com.swymnation.dms.controller;

import com.swymnation.dms.model.*;
import com.swymnation.dms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportAggregator aggregator;
    private final ReportGeneratorService generator;

    @Autowired
    public ReportController(ReportAggregator agg, ReportGeneratorService gen) {
        this.aggregator = agg;
        this.generator = gen;
    }

    @GetMapping("/generate")
    public ResponseEntity<?> getReport(@RequestParam String type, 
                                       @RequestHeader("X-Role") String role) {
        try {
            User admin = new User("MockAdmin", "admin@swym.com", UserRole.valueOf(role));
            
            ReportCriteria criteria = new ReportCriteria(
                LocalDate.now().minusDays(30), LocalDate.now(), null, null, type
            );
            
            ReportData data = aggregator.generateReportData(criteria);
            String pdf = generator.exportReport(data, admin, ReportFormat.PDF);
            
            return ResponseEntity.ok(pdf);
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Access Denied: " + e.getMessage());
        }
    }
}