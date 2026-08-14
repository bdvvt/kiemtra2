package com.example.duanlon2.models.entities;

import com.example.duanlon2.models.constants.EnrollmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments",uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({"password", "roles", "otpCode", "otpExpiration","status"})
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnrollmentStatus status = EnrollmentStatus.ENROLLED;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Column(name = "progress_percent", precision = 5, scale = 2)
    private BigDecimal progressPercent = BigDecimal.ZERO;

    @PrePersist
    protected void onCreate() {
        this.enrollmentDate = LocalDateTime.now();
        if (progressPercent == null) progressPercent = BigDecimal.ZERO;
        if (status == null) status = EnrollmentStatus.ENROLLED;
    }
}

