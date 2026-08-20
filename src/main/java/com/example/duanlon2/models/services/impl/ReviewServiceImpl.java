package com.example.duanlon2.models.services.impl;

import com.example.duanlon2.exceptions.BadRequestException;
import com.example.duanlon2.exceptions.NotFoundException;
import com.example.duanlon2.models.constants.RoleName;
import com.example.duanlon2.models.constants.UserStatus;
import com.example.duanlon2.models.dto.req.ReviewReq;
import com.example.duanlon2.models.entities.Course;
import com.example.duanlon2.models.entities.Review;
import com.example.duanlon2.models.entities.User;
import com.example.duanlon2.models.repositories.ICourseRepository;
import com.example.duanlon2.models.repositories.IEnrollmentRepository;
import com.example.duanlon2.models.repositories.IReviewRepository;
import com.example.duanlon2.models.repositories.IRoleRepository;
import com.example.duanlon2.models.services.IEnrollmentService;
import com.example.duanlon2.models.services.IReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {
    private final IReviewRepository reviewRepository;
    private final ICourseRepository courseRepository;
    private final IEnrollmentRepository enrollmentRepository;
    private final IRoleRepository roleRepository;

    @Override
    public List<Review> getReviewsByCourseId(Long courseId) {
        courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với ID: " + courseId));
        return reviewRepository.findByCourseCourseIdOrderByCreatedAtDesc(courseId);
    }

    @Override
    public Review addReview(ReviewReq req,Long courseId, User student) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với ID: " + courseId));
        boolean isEnrolled = enrollmentRepository.existsByStudentIdAndCourseCourseId(student.getId(), courseId);
        if (!isEnrolled) {
            throw new BadRequestException("Bạn chưa đăng ký khóa học này nên không thể viết đánh giá");
        }
        boolean hasReviewed = reviewRepository.existsByCourseCourseIdAndStudentId(courseId, student.getId());
        if (hasReviewed) {
            throw new BadRequestException("Bạn đã đánh giá khóa học này trước đó rồi");
        }
        log.info("Saving new Review entity to database ");
        Review review = Review.builder()
                .course(course)
                .student(student)
                .rating(req.getRating())
                .comment(req.getComment())
                .build();
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(User currentUser, Long reviewId, ReviewReq req) {
        log.info("User {} updating review ID: {}", currentUser.getUsername(), reviewId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đánh giá với ID: " + reviewId));
        Set<RoleName> roleNames = roleRepository.findRoleNamesByUserId(currentUser.getId());
        boolean isAdmin = roleNames.contains(RoleName.ADMIN);
        boolean isOwner = review.getStudent().getId().equals(currentUser.getId());
        if (!isAdmin && !isOwner) {
            throw new BadRequestException("Bạn không có quyền chỉnh sửa đánh giá này");
        }
        review.setRating(req.getRating());
        review.setComment(req.getComment());
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(User currentUser, Long reviewId) {
        log.info("Deleting Review record with ID: {}", reviewId);
        Review deleteReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đánh giá với ID: " + reviewId));
        Set<RoleName> roleNames = roleRepository.findRoleNamesByUserId(currentUser.getId());
        boolean isAdmin = roleNames.contains(RoleName.ADMIN);
        boolean isOwner = deleteReview.getStudent().getId().equals(currentUser.getId());
        if (!isAdmin && !isOwner) {
            throw new BadRequestException("Bạn không có quyền xoa đánh giá này");
        }
        reviewRepository.delete(deleteReview);

    }
}
