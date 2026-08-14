package com.example.duanlon2.models.repositories;

import com.example.duanlon2.models.entities.Course;
import com.example.duanlon2.models.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILessonRepository extends JpaRepository<Lesson, Long> {
    @Query("""
                    SELECT l
                    FROM Lesson l
                    WHERE l.lessonId = :id
                    AND (l IS NULL OR l.isPublished = true)
    """)
    Optional<Lesson> findByIdWithPublishedLessons(@Param("id") Long id);
}

