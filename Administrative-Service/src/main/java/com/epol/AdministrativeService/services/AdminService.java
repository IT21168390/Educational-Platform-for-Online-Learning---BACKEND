package com.epol.AdministrativeService.services;

import com.epol.AdministrativeService.consts.EnrollmentStatus;
import com.epol.AdministrativeService.consts.Status;
import com.epol.AdministrativeService.dao.CourseDAO;
import com.epol.AdministrativeService.dao.EnrollmentDAO;

import java.util.List;

public interface AdminService {
    CourseDAO updateStatus(String courseId, String courseName,  Status status);

}