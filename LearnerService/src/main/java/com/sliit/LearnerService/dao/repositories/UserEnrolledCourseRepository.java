package com.sliit.LearnerService.dao.repositories;

import com.sliit.LearnerService.dao.domain.UserEnrolledCourse;
import com.sliit.LearnerService.dao.dto.LearnerProgressDto;
import com.sliit.LearnerService.util.enums.CommonStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEnrolledCourseRepository extends JpaRepository<UserEnrolledCourse, Integer> {
    public UserEnrolledCourse findByEnrolledCourseIdAndUserIdAndStatus(String enrolledCourseId, String userId, CommonStatus status);
    public boolean existsByEnrolledCourseIdAndUserIdAndStatus(String enrolledCourseId, String userId, CommonStatus status);
    public List<String> findByUserIdAndStatus(String userId, CommonStatus status);

    public List<UserEnrolledCourse> findAllByEnrolledCourseId(String courseId);
    public UserEnrolledCourse findByEnrolledCourseIdAndUserId(String courseId, String userId);

}