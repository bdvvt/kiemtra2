package com.example.duanlon2.models.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_progress", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"enrollment_id", "lesson_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progressId;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(name = "is_completed")
    private Boolean isCompleted = false;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @UpdateTimestamp
    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    @PrePersist
    protected void onCreate() {
        if (this.isCompleted == null) {
            this.isCompleted = false;
        }
        this.lastAccessedAt = LocalDateTime.now();
    }
}
