package com.example.duanlon2.models.services;

import com.example.duanlon2.models.constants.CourseStatus;
import com.example.duanlon2.models.dto.req.CourseReq;
import com.example.duanlon2.models.dto.req.CourseStatusReq;
import com.example.duanlon2.models.dto.req.UserReq;
import com.example.duanlon2.models.entities.Course;
import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.entities.Lesson;
import com.example.duanlon2.models.dto.req.LessonReq;
import java.util.List;

public interface ICourseService {
    List<Course> findAll(User currentUser,CourseStatus courseStatus);
    Course findByIdWithPublishedLessons(Long courseId);
    List<Lesson> getPublishedLessons(Long courseId);
    Lesson addLessonToCourse(Long courseId, LessonReq req, Long teacherId);
    Course createCourse(CourseReq req);
    Course updateCourse(Long id, CourseReq req);
    Course updateCourseStatus(Long courseId, CourseStatusReq req);
    void deleteCourse(Long id);
    List<Course> findAllBySearch(String search);
    List<Course> findByTeacherId(Long teacherId);
}
