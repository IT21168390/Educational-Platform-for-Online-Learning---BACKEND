package com.epol.AdministrativeService.models;

import com.epol.AdministrativeService.consts.CourseContentWeights;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Video {
    private String video_Url;
    private float weight = CourseContentWeights.COURSE_VIDEO_WEIGHT;
    private String description;
}
