package com.example.duanlon2.models.services.impl;

import com.example.duanlon2.exceptions.NotFoundException;
import com.example.duanlon2.models.constants.CourseStatus;
import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.dto.req.CourseReq;
import com.example.duanlon2.models.dto.req.CourseStatusReq;
import com.example.duanlon2.models.dto.req.LessonReq;
import com.example.duanlon2.models.dto.res.TeacherCourseOverviewItem;
import com.example.duanlon2.models.dto.res.TeacherCoursesOverviewRes;
import com.example.duanlon2.models.dto.res.TopCourseRes;
import com.example.duanlon2.models.entities.Course;
import com.example.duanlon2.models.entities.Enrollment;
import com.example.duanlon2.models.entities.Lesson;
import com.example.duanlon2.models.repositories.IEnrollmentRepository;
import com.example.duanlon2.models.repositories.ILessonRepository;
import com.example.duanlon2.models.services.uploads.UploadService;
import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.repositories.ICourseRepository;
import com.example.duanlon2.models.repositories.IUserRepository;
import com.example.duanlon2.models.services.ICourseService;
import com.example.duanlon2.models.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements ICourseService {
    private final ICourseRepository courseRepository;
    private final IUserRepository userRepository;
    private final ILessonRepository lessonRepository;
    private final IEnrollmentRepository enrollmentRepository;
    private final UploadService uploadService;

    @Override
    public List<Course> findAll(User currentUser,CourseStatus status) {
        log.info("User {} fetching courses with status filter: {}", currentUser.getUsername(), status);

        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleName() == RoleName.ADMIN);
        if (!isAdmin) {
            if (status != null && status != CourseStatus.PUBLISHED) {
                return List.of();
            }
            return courseRepository.findAllByStatus(CourseStatus.PUBLISHED);
        }
        if (status == null) {
            return courseRepository.findAll();
        }
        return courseRepository.findAllByStatus(status);
    }

    @Override
    public Course findByIdWithPublishedLessons(Long courseId) {
        log.info("Fetching course details for ID: {} with published lessons", courseId);
        return courseRepository.findByIdWithPublishedLessons(courseId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với ID: " + courseId));
    }

    @Override
    public List<Lesson> getPublishedLessons(Long courseId) {
        log.info("Fetching published lessons for course ID: {}", courseId);
        Course course = courseRepository.findByIdWithPublishedLessons(courseId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với ID: " + courseId));
        return course.getLessons();
    }

    @Override
    public Lesson addLessonToCourse(Long courseId, LessonReq req, Long teacherId) {
        log.info("Adding new lesson to course ID: {} by user ID: {}", courseId, teacherId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với ID: " + courseId));
        course = courseRepository.findByCourseIdAndTeacherId(courseId, teacherId)
                .orElseThrow(() -> new AccessDeniedException("Bạn không phải giảng viên phụ trách khóa học này nên không có quyền thêm bài học!"));

        Lesson lesson = Lesson.builder()
                .course(course)
                .title(req.getTitle())
                .contentUrl(uploadService.upload(req.getContentUrl()))
                .textContent(req.getTextContent())
                .orderIndex(req.getOrderIndex())
                .isPublished(req.getIsPublished() != null ? req.getIsPublished() : false)
                .build();

        return lessonRepository.save(lesson);
    }

    @Override
    public Course createCourse(CourseReq req) {
        User teacher = userRepository.findTeacherById(req.getTeacherId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giảng viên hợp lệ với ID: " + req.getTeacherId()));

        log.info("Saving new Course entity to database for title: {}", req.getTitle());
        Course course = Course.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .teacher(teacher)
                .price(req.getPrice())
                .durationHours(req.getDurationHours())
                .status(CourseStatus.DRAFT)
                .build();
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourseStatus(Long courseId, CourseStatusReq req) {
        log.info("Updating course status for ID: {} to {}", courseId, req.getStatus());
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với ID: " + courseId));

        course.setStatus(req.getStatus());
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, CourseReq req) {
        Course update = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với ID: " + id));
        User teacher = userRepository.findTeacherById(req.getTeacherId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giảng viên hợp lệ với ID: " + req.getTeacherId()));
        update.setTitle(req.getTitle());
        update.setDescription(req.getDescription());
        update.setTeacher(teacher);
        update.setPrice(req.getPrice());
        update.setDurationHours(req.getDurationHours());
        update.setStatus(CourseStatus.DRAFT);
        return courseRepository.save(update);
    }

    @Override
    public void deleteCourse(Long id) {
        log.info("Deleting course record with ID: {}", id);
        Course deleteCourse = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với ID: " + id));
        courseRepository.delete(deleteCourse);
    }

    @Override
    public List<Course> findAllBySearch(String search) {
        log.info("Fetching courses with search term: {}", search);
        if (search == null || search.toString().isEmpty()){
            return courseRepository.findAll();
        }
        return courseRepository.findAllBySearch(search);
    }

    @Override
    public List<Course> findByTeacherId(Long teacherId) {
        log.info("Fetching courses with id teacher term: {}", teacherId);
        if (teacherId == null || teacherId.toString().isEmpty()){
            return courseRepository.findAll();
        }
        return courseRepository.findByTeacherId(teacherId);
    }

    @Override
    public List<TopCourseRes> findTopCourses() {
        log.info("Fetching top courses by enrollment count");
        return courseRepository.findTopCourses();
    }

    @Override
    public TeacherCoursesOverviewRes getTeacherCoursesOverview(Long teacherId) {
        log.info("Fetching courses overview for teacher ID: {}", teacherId);
        User teacher = userRepository.findTeacherById(teacherId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giảng viên với ID: " + teacherId));
        List<Course> courses = courseRepository.findByTeacherId(teacherId);
        List<TeacherCourseOverviewItem> courseItems = new ArrayList<>();
        long totalEnrollments = 0;
        for (Course course : courses) {
            List<Enrollment> enrollments = enrollmentRepository.findByCourseCourseId(course.getCourseId());
            BigDecimal courseProgress = enrollments.stream()
                    .map(Enrollment::getProgressPercent)
                    .filter(progress -> progress != null)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            totalEnrollments += enrollments.size();
            courseItems.add(TeacherCourseOverviewItem.builder()
                    .courseId(course.getCourseId())
                    .title(course.getTitle())
                    .status(course.getStatus())
                    .enrollmentCount(enrollments.size())
                    .build());
        }

        int publishedCourses = (int) courses.stream()
                .filter(course -> course.getStatus() == CourseStatus.PUBLISHED)
                .count();

        return TeacherCoursesOverviewRes.builder()
                .teacherId(teacher.getId())
                .teacherName(teacher.getFullName())
                .email(teacher.getEmail())
                .totalCourses(courses.size())
                .publishedCourses(publishedCourses)
                .totalEnrollments(totalEnrollments)
                .courses(courseItems)
                .build();
    }
}

