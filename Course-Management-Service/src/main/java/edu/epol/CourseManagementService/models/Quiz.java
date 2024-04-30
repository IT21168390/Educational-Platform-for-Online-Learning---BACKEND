package edu.epol.CourseManagementService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Quiz {
    @Id
    private String id;
    private String question;
    private String answer;
    private float weight;
}
