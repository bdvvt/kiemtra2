package com.example.duanlon2.models.repositories;

import com.example.duanlon2.models.dto.res.EnrollmentProgressStats;
import com.example.duanlon2.models.dto.res.StudentCourseProgressQuery;
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

    @Query("""
        SELECT COALESCE(AVG(COALESCE(e.progressPercent, 0)), 0)
        FROM Enrollment e
        JOIN e.course c
        WHERE c.teacher.id = :teacherId
    """)
    Double findAverageProgressByTeacherId(@Param("teacherId") Long teacherId);

    @Query("""
        SELECT new com.example.duanlon2.models.dto.res.EnrollmentProgressStats(
            COUNT(DISTINCT l.lessonId),
            COUNT(DISTINCT lp.progressId),
            CASE WHEN COUNT(DISTINCT l.lessonId) = 0 THEN 0.0
                 ELSE 100.0 * COUNT(DISTINCT lp.progressId) / COUNT(DISTINCT l.lessonId)
            END
        )
        FROM Enrollment e
        JOIN e.course c
        LEFT JOIN c.lessons l
        LEFT JOIN LessonProgress lp
               ON lp.enrollment = e
              AND lp.lesson = l
              AND lp.isCompleted = true
        WHERE e.enrollmentId = :enrollmentId
        GROUP BY e.enrollmentId
    """)
    Optional<EnrollmentProgressStats> findProgressStatsByEnrollmentId(
            @Param("enrollmentId") Long enrollmentId
    );

    @Query("""
        SELECT new com.example.duanlon2.models.dto.res.StudentCourseProgressQuery(
            e.enrollmentId,
            c.courseId,
            c.title,
            e.enrollmentDate,
            e.status,
            e.completionDate,
            COUNT(DISTINCT l.lessonId),
            COUNT(DISTINCT lp.progressId),
            CASE WHEN COUNT(DISTINCT l.lessonId) = 0 THEN 0.0
                 ELSE 100.0 * COUNT(DISTINCT lp.progressId) / COUNT(DISTINCT l.lessonId)
            END
        )
        FROM Enrollment e
        JOIN e.course c
        LEFT JOIN c.lessons l
        LEFT JOIN LessonProgress lp
               ON lp.enrollment = e
              AND lp.lesson = l
              AND lp.isCompleted = true
        WHERE e.student.id = :studentId
        GROUP BY e.enrollmentId, c.courseId, c.title,
                 e.enrollmentDate, e.status, e.completionDate
        ORDER BY e.enrollmentDate DESC
    """)
    List<StudentCourseProgressQuery> findStudentCourseProgressByStudentId(
            @Param("studentId") Long studentId
    );
}
