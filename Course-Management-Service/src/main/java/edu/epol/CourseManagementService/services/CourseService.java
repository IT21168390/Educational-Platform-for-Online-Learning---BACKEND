package edu.epol.CourseManagementService.services;

import edu.epol.CourseManagementService.dao.CourseDAO;
import edu.epol.CourseManagementService.models.Course;
import edu.epol.CourseManagementService.models.LectureNote;
import edu.epol.CourseManagementService.models.Quiz;
import edu.epol.CourseManagementService.models.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    //String uploadFile(MultipartFile file, String details) throws IOException;
    List<CourseDAO> findAll();
    CourseDAO findCourseById(String courseId);
    CourseDAO createCourse(CourseDAO courseDAO);
    CourseDAO updateFullCourse(CourseDAO courseDAO);
    CourseDAO updateBasicCourseInformation(String courseId, String courseName, double price);
    LectureNote uploadLectureNote(String courseId, MultipartFile file, String description, float weight);
    Video uploadCourseVideo(String courseId, MultipartFile file, String description, float weight);
    List<Quiz> addCourseQuizzes(String courseId, List<Quiz> quizList) throws IOException;
    List<Quiz> removeQuiz(String courseId, Quiz quiz);
    void removeCourse(String courseId);
}
