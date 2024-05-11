package com.epol.AdministrativeService.converters;

import com.epol.AdministrativeService.dao.EnrollmentDAO;
import com.epol.AdministrativeService.models.Enrollment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentDAOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public EnrollmentDAO convertCourseToEnrollmentDAO(Enrollment enrollment) {
        return modelMapper.map(enrollment, EnrollmentDAO.class);
    }

    public Enrollment convertEnrollmentDAOToEnrollment(EnrollmentDAO enrollmentDAO) {
        return modelMapper.map(enrollmentDAO, Enrollment.class);
    }
}
