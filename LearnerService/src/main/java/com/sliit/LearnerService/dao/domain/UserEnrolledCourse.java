package com.sliit.LearnerService.dao.domain;

import com.sliit.LearnerService.util.enums.CommonStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_enrolled_course")
public class UserEnrolledCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String enrolledCourseId;
    private Date enrolledDate;
    private CommonStatus status;
    private String userId;
    private Boolean quiz;
    private Boolean note;
    private Boolean video;
    //private CommonStatus paymentStatus;

}