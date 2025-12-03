package com.swymnation.dms.repository;

import com.swymnation.dms.model.ProgressUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProgressUpdateRepository extends JpaRepository<ProgressUpdate, Long> {
    // [REQ-FR04.4] Maintain historical log
    List<ProgressUpdate> findByStudentIdOrderByTimestampDesc(Long studentId);
}