package edu.epol.CourseManagementService.repositories;

import edu.epol.CourseManagementService.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
}
