package com.example.duanlon2.models.services;

import com.example.duanlon2.models.dto.req.CourseReq;
import com.example.duanlon2.models.dto.req.ReviewReq;
import com.example.duanlon2.models.dto.req.UserPassReq;
import com.example.duanlon2.models.entities.Course;
import com.example.duanlon2.models.entities.Review;
import com.example.duanlon2.models.entities.User;

import java.util.List;

public interface IReviewService {
    List<Review> getReviewsByCourseId(Long courseId);
    Review addReview(ReviewReq req,Long courseId, User student);
    Review updateReview(User currentUser,Long reviewId, ReviewReq req);
    void  deleteReview(User currentUser,Long reviewId);
}
