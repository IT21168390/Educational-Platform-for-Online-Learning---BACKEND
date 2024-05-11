package edu.epol.CourseManagementService.models;

import edu.epol.CourseManagementService.consts.CourseContentWeights;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LectureNote {
    /*@Id
    private String id;*/
    private String note_Url;
    private float weight = CourseContentWeights.LECTURE_NOTES_WEIGHT;
    private String description;
}
