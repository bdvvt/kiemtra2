package com.example.duanlon2.models.repositories;

import com.example.duanlon2.models.constants.CourseStatus;
import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.constants.UserStatus;
import com.example.duanlon2.models.entities.Course;
import com.example.duanlon2.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICourseRepository extends JpaRepository<Course,Long> {

    @Query("""
                    SELECT DISTINCT c
                    FROM Course c
                    LEFT JOIN FETCH c.lessons l
                    WHERE c.courseId = :courseId
                      AND (l IS NULL OR l.isPublished = true)
    """)
    Optional<Course> findByIdWithPublishedLessons(@Param("courseId") Long courseId);

    @Query("SELECT c FROM Course c WHERE c.courseId = :courseId AND c.teacher.id = :teacherId")
    Optional<Course> findByCourseIdAndTeacherId(@Param("courseId") Long courseId, @Param("teacherId") Long teacherId);

    @Query("SELECT c FROM Course c WHERE c.title LIKE concat('%',:search,'%') OR c.description LIKE concat('%',:search,'%')")
    List<Course> findAllBySearch(@Param("search") String search);

    List<Course> findByTeacherId(Long teacherId);

    @Query("""
        SELECT c 
        FROM Course c
        WHERE (:status IS NULL OR c.status = :status)
    """)
    List<Course> findAllByStatus(@Param("status") CourseStatus status);
}
