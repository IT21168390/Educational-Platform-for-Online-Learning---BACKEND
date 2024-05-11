package com.stripe.payment.dao;
import com.stripe.payment.consts.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDAO {
    private String id;
//    private Course enrolledCourses;
    private Long learnerId;
    private EnrollmentStatus enrollmentStatus;
}
