package com.epol.AdministrativeService.repositories;

import com.epol.AdministrativeService.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
}
