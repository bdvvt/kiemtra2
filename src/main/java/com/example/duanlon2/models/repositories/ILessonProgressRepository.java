package com.example.duanlon2.models.repositories;

import com.example.duanlon2.models.entities.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    Optional<LessonProgress> findByEnrollmentEnrollmentIdAndLessonLessonId(Long enrollmentId, Long lessonId);

}
