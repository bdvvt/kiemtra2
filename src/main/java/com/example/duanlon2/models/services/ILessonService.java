package com.example.duanlon2.models.services;

import com.example.duanlon2.models.dto.req.CourseReq;
import com.example.duanlon2.models.dto.req.LessonPublishReq;
import com.example.duanlon2.models.dto.req.LessonReq;
import com.example.duanlon2.models.entities.Course;
import com.example.duanlon2.models.entities.Lesson;
import com.example.duanlon2.models.entities.User;

public interface ILessonService {
    Lesson findByIdPublishedLessons(Long id);
    Lesson updateLesson(Long id, LessonReq req);
    Lesson updateLessonPublish(Long id, LessonPublishReq req);
    void deleteLesson(Long id);
}
