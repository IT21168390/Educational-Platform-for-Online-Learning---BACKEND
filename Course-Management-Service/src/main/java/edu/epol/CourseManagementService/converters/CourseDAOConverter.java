package edu.epol.CourseManagementService.converters;

import edu.epol.CourseManagementService.dao.CourseDAO;
import edu.epol.CourseManagementService.models.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseDAOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public CourseDAO convertCourseToCourseDAO(Course course) {
        return modelMapper.map(course, CourseDAO.class);
    }

    public Course convertCourseDAOToCourse(CourseDAO courseDTO) {
        return modelMapper.map(courseDTO, Course.class);
    }
}
