package com.swymnation.dms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress_updates")
public class ProgressUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Integer skillLevel; // 1-8
    
    @Column(length = 500) // [AC-FR04.2] Max 500 chars
    private String notes;

    // [REQ-FR04.2] Digital Signature (Instructor ID + Timestamp)
    private Long instructorId;
    private LocalDateTime timestamp;

    public ProgressUpdate() {}

    public ProgressUpdate(Long studentId, Integer skillLevel, String notes, Long instructorId) {
        this.studentId = studentId;
        this.skillLevel = skillLevel;
        this.notes = notes;
        this.instructorId = instructorId;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public Integer getSkillLevel() { return skillLevel; }
    public String getNotes() { return notes; }
    public Long getInstructorId() { return instructorId; }
    public LocalDateTime getTimestamp() { return timestamp; }
}