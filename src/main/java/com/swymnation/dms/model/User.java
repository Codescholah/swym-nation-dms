package com.swymnation.dms.model;

// CORRECTION: Spring Boot 3 uses 'jakarta', not 'javax'
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // [FR-04] Linkage for Parents to access specific Student records
    @Column(name = "assigned_student_id")
    private Long assignedStudentId;

    // [FR-06] Notification Preferences
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_notification_prefs", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "preference")
    private Set<String> notificationPreferences = new HashSet<>();

    public User() {}

    public User(String username, String email, UserRole role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    public Long getAssignedStudentId() { return assignedStudentId; }
    public void setAssignedStudentId(Long assignedStudentId) { this.assignedStudentId = assignedStudentId; }
    public Set<String> getNotificationPreferences() { return notificationPreferences; }
    public void setNotificationPreferences(Set<String> prefs) { this.notificationPreferences = prefs; }
    
    // Helper method for FR-06 checks
    public boolean hasPreference(String preferenceKey) {
        return notificationPreferences.contains(preferenceKey);
    }
}