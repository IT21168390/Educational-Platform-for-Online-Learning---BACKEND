package edu.epol.CourseManagementService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@NoArgsConstructor
@Data
/*@CompoundIndexes({
        @CompoundIndex(name = "course_question_index", def = "{'question': 1, 'courseId': 1}", unique = true)
})*/
public class Quiz {
    @Id
    private String id;
    //@Indexed(unique = true)
    private String question;
    private String answer;
    private String[] options = new String[4];
    private float weight;
}
