package com.swymnation.dms.service;

import com.swymnation.dms.model.ProgressUpdate;
import com.swymnation.dms.model.User;
import com.swymnation.dms.model.UserRole;
import com.swymnation.dms.repository.ProgressUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProgressManager {

    private final ProgressUpdateRepository repository;
    private final AccessControl accessControl;

    @Autowired
    public ProgressManager(ProgressUpdateRepository repository, AccessControl accessControl) {
        this.repository = repository;
        this.accessControl = accessControl;
    }

    @Transactional
    public ProgressUpdate updateProgress(User instructor, Long studentId, Integer level, String notes) {
        // 1. Security Check [AC-FR04.3] (Only Instructors/Admins can modify)
        accessControl.enforceAuthorization(instructor, "Update Progress", UserRole.INSTRUCTOR, UserRole.ADMINISTRATOR);

        // 2. Data Validation [AC-FR04.2]
        if (notes != null && notes.length() > 500) {
            throw new IllegalArgumentException("Notes cannot exceed 500 characters.");
        }

        // 3. Save Update
        ProgressUpdate update = new ProgressUpdate(studentId, level, notes, instructor.getId());
        return repository.save(update);
    }

    public List<ProgressUpdate> getStudentHistory(User user, Long studentId) {
        // 1. Security Check [REQ-FR04.3] (Parents can view ONLY their own child)
        accessControl.enforceStudentAccess(user, studentId);

        // 2. Retrieve History
        return repository.findByStudentIdOrderByTimestampDesc(studentId);
    }
}