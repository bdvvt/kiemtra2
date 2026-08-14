package com.example.duanlon2.models.services;

import com.example.duanlon2.models.dto.req.EnrollmentReq;
import com.example.duanlon2.models.entities.Enrollment;

import java.util.List;

public interface IEnrollmentService {
    List<Enrollment> getStudentEnrollments(Long studentId);
    Enrollment enrollCourse(EnrollmentReq req, Long studentId);
    Enrollment getEnrollmentDetail(Long enrollmentId, Long studentId);
    Enrollment completeLesson(Long enrollmentId, Long lessonId, Long studentId);
}
