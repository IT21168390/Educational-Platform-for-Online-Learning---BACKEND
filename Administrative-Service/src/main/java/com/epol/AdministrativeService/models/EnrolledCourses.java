package com.epol.AdministrativeService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnrolledCourses {
    private List<com.epol.AdministrativeService.models.Course> courses;
}
