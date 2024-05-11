package com.epol.AdministrativeService.models;

import com.epol.AdministrativeService.consts.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("enrollments")

public class Enrollment {

    @Id
    private String id;
//    private EnrolledCourses enrolledCourses;
    private Long learnerId;
    private EnrollmentStatus enrollmentStatus;
}
