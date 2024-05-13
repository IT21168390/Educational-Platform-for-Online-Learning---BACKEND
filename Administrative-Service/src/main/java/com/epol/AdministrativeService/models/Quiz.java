package com.epol.AdministrativeService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Quiz {
    @Id
    private String id;
    //@Indexed(unique = true)
    private String question;
    private String answer;
    private String[] options = new String[4];
    private float weight;
}
