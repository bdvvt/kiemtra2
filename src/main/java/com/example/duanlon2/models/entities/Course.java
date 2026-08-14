package com.example.duanlon2.models.entities;

import com.example.duanlon2.models.constants.CourseStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIgnoreProperties({"password", "roles", "otpCode", "otpExpiration","status"})
    private User teacher;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons ;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "duration_hours")
    private Long durationHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourseStatus status = CourseStatus.DRAFT;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
