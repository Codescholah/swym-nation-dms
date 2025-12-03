package com.swymnation.dms.service;

import com.swymnation.dms.model.Attendance;
import com.swymnation.dms.model.User;
import com.swymnation.dms.model.UserRole;
import com.swymnation.dms.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceManager {

    private final AttendanceRepository attendanceRepository;
    private final AccessControl accessControl;

    @Autowired
    public AttendanceManager(AttendanceRepository repo, AccessControl accessControl) {
        this.attendanceRepository = repo;
        this.accessControl = accessControl;
    }

    @Transactional
    public List<Attendance> recordBatchAttendance(User instructor, List<Attendance> records) {
        // 1. Security Check [FR-02 SR 2]
        accessControl.enforceAuthorization(instructor, "Mark Attendance", UserRole.INSTRUCTOR, UserRole.ADMINISTRATOR);

        // 2. Performance Optimization: Save All [Perf NFR < 3s]
        return attendanceRepository.saveAll(records);
    }

    public List<Attendance> getHistory(User user, Long studentId) {
        // 1. Security Check
        accessControl.enforceStudentAccess(user, studentId);

        // 2. Retrieve
        return attendanceRepository.findByStudentId(studentId);
    }
}