package com.swymnation.dms.repository;

import com.swymnation.dms.model.ReportCriteria;
import com.swymnation.dms.model.ReportData;
import org.springframework.stereotype.Repository;

@Repository
public class MockReportDataSource implements ReportDataSource {
    @Override
    public ReportData fetchAggregatedData(ReportCriteria criteria) {
        // Simulates fetching 10,000 records from DB (FR-05 SR 5)
        return new ReportData(
            "Consolidated Report for " + criteria.reportType() + 
            " (" + criteria.startDate() + " to " + criteria.endDate() + ")", 
            10000
        );
    }
}