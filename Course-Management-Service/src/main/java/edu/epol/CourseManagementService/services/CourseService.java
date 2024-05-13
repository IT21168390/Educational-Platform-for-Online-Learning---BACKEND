package edu.epol.CourseManagementService.services;

import edu.epol.CourseManagementService.consts.Status;
import edu.epol.CourseManagementService.dao.BasicCourseDTO;
import edu.epol.CourseManagementService.dao.CourseDAO;
import edu.epol.CourseManagementService.models.LectureNote;
import edu.epol.CourseManagementService.models.Quiz;
import edu.epol.CourseManagementService.models.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    // Retrieve all Courses from the database.
    List<CourseDAO> findAll();

    // Retrieve a list of courses by the status of the course.
    List<CourseDAO> findCoursesByStatus(Status status);

    // Find a specific course.
    CourseDAO findCourseById(String courseId);

    // Add a new course to the system.
    CourseDAO createCourse(BasicCourseDTO basicCourseDTO);

    // Update all the data of a course.
    CourseDAO updateFullCourse(CourseDAO courseDAO);

    // Update course information such as course name, description, and price only.
    CourseDAO updateBasicCourseInformation(String courseId, String courseName, String description, double price);

    // Upload Lecture Note file with relevant information to a specific course.
    LectureNote uploadLectureNote(String courseId, MultipartFile file, String description, float weight);

    // Upload Lecture Video file with relevant information to a specific course.
    Video uploadCourseVideo(String courseId, MultipartFile file, String description, float weight);

    // Add Quizzes to a specific course.
    List<Quiz> addCourseQuizzes(String courseId, List<Quiz> quizList) throws IOException;

    // Remove Quizzes from a selected course.
    List<Quiz> removeQuiz(String courseId, Quiz quiz);

    // Remove a course from the database.
    void removeCourse(String courseId);
}
