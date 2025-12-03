package com.swymnation.dms.service;

import com.swymnation.dms.model.User;
import com.swymnation.dms.model.UserRole;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccessControl {

    public void enforceAuthorization(User user, String operationName, UserRole... allowedRoles) {
        if (user == null) {
            throw new SecurityException("Unauthenticated user attempted " + operationName);
        }

        Set<UserRole> requiredRoles = Arrays.stream(allowedRoles).collect(Collectors.toSet());

        if (!requiredRoles.contains(user.getRole())) {
            throw new SecurityException("ACCESS DENIED: User " + user.getUsername() + " is not authorized for " + operationName);
        }
    }

    // Specific check for FR-04 (Student Progress)
    public void enforceStudentAccess(User user, Long targetStudentId) {
        if (user == null) throw new SecurityException("Unauthenticated access.");

        // Admins and Instructors have global access
        if (user.getRole() == UserRole.ADMINISTRATOR || user.getRole() == UserRole.INSTRUCTOR) return;

        // Parents/Clients must match the student ID
        if ((user.getRole() == UserRole.PARENT || user.getRole() == UserRole.CLIENT) && 
            user.getAssignedStudentId() != null && 
            user.getAssignedStudentId().equals(targetStudentId)) {
            return;
        }

        throw new SecurityException("ACCESS DENIED: User cannot access this student record.");
    }
}