package com.epol.AdministrativeService.clients;

import com.epol.AdministrativeService.dao.CourseDAO;
import com.epol.AdministrativeService.models.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface CourseClient {
    @GetExchange(value = "/api/v1/courses/public/all")
    public ResponseEntity<List<CourseDAO>> getAllCourses();

}
