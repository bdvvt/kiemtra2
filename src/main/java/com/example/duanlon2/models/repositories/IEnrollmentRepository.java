package com.example.duanlon2.models.repositories;

import com.example.duanlon2.models.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId")
    List<Enrollment> findByStudentId(@Param("studentId") Long studentId);
    boolean existsByCourseCourseIdAndStudentId(Long courseId, Long studentId);
    List<Enrollment> findByCourseCourseId(Long courseId);
    boolean existsByStudentIdAndCourseCourseId(Long studentId, Long courseId);

}
