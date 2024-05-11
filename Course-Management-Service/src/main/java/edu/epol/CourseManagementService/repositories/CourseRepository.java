package edu.epol.CourseManagementService.repositories;

import edu.epol.CourseManagementService.consts.Status;
import edu.epol.CourseManagementService.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    List<Course> findAllByStatus(Status status);
}
