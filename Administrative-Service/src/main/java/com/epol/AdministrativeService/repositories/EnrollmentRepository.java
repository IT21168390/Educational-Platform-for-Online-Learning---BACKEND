package com.epol.AdministrativeService.repositories;
import com.epol.AdministrativeService.models.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {
    // You can add custom queries if needed
}
