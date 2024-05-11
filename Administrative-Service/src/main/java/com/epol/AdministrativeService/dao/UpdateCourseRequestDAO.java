package com.epol.AdministrativeService.dao;

import com.epol.AdministrativeService.consts.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCourseRequestDAO {
    private String courseName;
    private Status status;
}

