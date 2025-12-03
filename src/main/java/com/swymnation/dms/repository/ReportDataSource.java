package com.swymnation.dms.repository;

import com.swymnation.dms.model.ReportCriteria;
import com.swymnation.dms.model.ReportData;

public interface ReportDataSource {
    ReportData fetchAggregatedData(ReportCriteria criteria);
}