package edu.epol.CourseManagementService.repositories;

import edu.epol.CourseManagementService.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
}
