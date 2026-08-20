package com.example.duanlon2.models.repositories;

import com.example.duanlon2.models.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByCourseCourseIdOrderByCreatedAtDesc(Long courseId);
    boolean existsByCourseCourseIdAndStudentId(Long courseId, Long studentId);
}
