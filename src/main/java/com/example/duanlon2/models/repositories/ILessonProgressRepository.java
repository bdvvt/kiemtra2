package com.example.duanlon2.models.repositories;

import com.example.duanlon2.models.entities.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    Optional<LessonProgress> findByEnrollmentEnrollmentIdAndLessonLessonId(Long enrollmentId, Long lessonId);

    @Query("SELECT COUNT(lp) FROM LessonProgress lp WHERE lp.enrollment.enrollmentId = :enrollmentId AND lp.isCompleted = true")
    long countCompletedLessonsByEnrollmentId(@Param("enrollmentId") Long enrollmentId);
}
