package com.example.duanlon2.models.services.impl;

import com.example.duanlon2.exceptions.NotFoundException;
import com.example.duanlon2.models.constants.EnrollmentStatus;
import com.example.duanlon2.models.dto.req.EnrollmentReq;
import com.example.duanlon2.models.entities.Course;
import com.example.duanlon2.models.entities.Enrollment;
import com.example.duanlon2.models.entities.Lesson;
import com.example.duanlon2.models.entities.LessonProgress;
import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.repositories.ICourseRepository;
import com.example.duanlon2.models.repositories.IEnrollmentRepository;
import com.example.duanlon2.models.repositories.ILessonProgressRepository;
import com.example.duanlon2.models.repositories.ILessonRepository;
import com.example.duanlon2.models.repositories.IUserRepository;
import com.example.duanlon2.models.services.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentServiceImpl implements IEnrollmentService {
    private final IEnrollmentRepository enrollmentRepository;
    private final IUserRepository userRepository;
    private final ICourseRepository courseRepository;
    private final ILessonRepository lessonRepository;
    private final ILessonProgressRepository lessonProgressRepository;

    @Override
    public List<Enrollment> getStudentEnrollments(Long studentId) {
        log.info("Fetching enrolled courses for student ID: {}", studentId);
        return enrollmentRepository.findByStudentId(studentId);
    }

    @Override
    public Enrollment enrollCourse(EnrollmentReq req, Long studentId) {
        log.info("Student ID: {} is enrolling in course ID: {}", studentId, req.getCourseId());
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với ID: " + req.getCourseId()));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin tài khoản sinh viên!"));
        if (enrollmentRepository.existsByCourseCourseIdAndStudentId(req.getCourseId(), studentId)) {
            throw new IllegalArgumentException("Sinh viên đã được đăng ký vào khóa học này trước đó!");
        }
        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .status(EnrollmentStatus.ENROLLED)
                .build();
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment getEnrollmentDetail(Long enrollmentId, Long studentId) {
        log.info("Fetching enrollment detail ID: {} for student ID: {}", enrollmentId, studentId);
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin đăng ký!"));
        if (!enrollment.getStudent().getId().equals(studentId)) {
            throw new AccessDeniedException("Bạn không có quyền xem thông tin đăng ký của sinh viên khác!");
        }
        return enrollment;
    }


    @Override
    public Enrollment completeLesson(Long enrollmentId, Long lessonId, Long studentId) {
        log.info("Student ID: {} is completing lesson ID: {} for enrollment ID: {}", studentId, lessonId, enrollmentId);
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin đăng ký với ID: " + enrollmentId));
        if (!enrollment.getStudent().getId().equals(studentId)) {
            throw new AccessDeniedException("Bạn không có quyền cập nhật tiến độ học của sinh viên khác!");
        }
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bài học với ID: " + lessonId));
        Long courseId = enrollment.getCourse().getCourseId();
        if (!lesson.getCourse().getCourseId().equals(courseId)) {
            throw new IllegalArgumentException("Bài học này không thuộc về khóa học bạn đã đăng ký!");
        }
        LessonProgress lessonProgress = lessonProgressRepository
                .findByEnrollmentEnrollmentIdAndLessonLessonId(enrollmentId, lessonId)
                .orElseGet(() -> LessonProgress.builder()
                        .enrollment(enrollment)
                        .lesson(lesson)
                        .isCompleted(false)
                        .build());
        lessonProgress.setIsCompleted(true);
        lessonProgress.setCompletedAt(java.time.LocalDateTime.now());
        lessonProgressRepository.save(lessonProgress);
        long totalLessons = enrollment.getCourse().getLessons().size();
        if (totalLessons > 0) {
            long completedLessons = lessonProgressRepository.countCompletedLessonsByEnrollmentId(enrollmentId);
            BigDecimal progressPercent = BigDecimal.valueOf(completedLessons)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(totalLessons), 2, java.math.RoundingMode.HALF_UP);
            enrollment.setProgressPercent(progressPercent);
            if (progressPercent.compareTo(new BigDecimal("100.00")) == 0) {
                enrollment.setStatus(EnrollmentStatus.COMPLETED);
                enrollment.setCompletionDate(LocalDateTime.now());
            } else {
                enrollment.setStatus(EnrollmentStatus.ENROLLED);
                enrollment.setCompletionDate(null);
            }
        }

        return enrollmentRepository.save(enrollment);
    }
}
