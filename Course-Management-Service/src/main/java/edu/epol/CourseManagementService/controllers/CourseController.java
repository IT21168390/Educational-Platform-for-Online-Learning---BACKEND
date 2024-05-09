package edu.epol.CourseManagementService.controllers;

import edu.epol.CourseManagementService.dao.BasicCourseDTO;
import edu.epol.CourseManagementService.dao.CourseDAO;
import edu.epol.CourseManagementService.models.Course;
import edu.epol.CourseManagementService.models.LectureNote;
import edu.epol.CourseManagementService.models.Quiz;
import edu.epol.CourseManagementService.models.Video;
import edu.epol.CourseManagementService.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    /*@PostMapping("/")
    public String addCourse(@RequestParam("file") MultipartFile file, @RequestParam("details") String details){
        try {
            return courseService.uploadFile(file, details);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/")
    public List<Course> getAllCourses(){
        return courseService.findAll();
    }*/
    @PostMapping(value = "/instructor/add-course"/*, consumes = "multipart/form-data"*/)
    public ResponseEntity<CourseDAO> addNewCourse(@RequestParam String name, @RequestParam String description, @RequestParam MultipartFile thumbnail, double price) {
        CourseDAO courseDAO = courseService.createCourse(new BasicCourseDTO(name, description, thumbnail, price));
        if (courseDAO==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courseDAO, HttpStatus.CREATED);
    }
    /*@PostMapping("/instructor/add-course")
    public ResponseEntity<CourseDAO> addNewCourse(@RequestBody CourseDAO course) {
        CourseDAO courseDAO = courseService.createCourse(course);
        if (courseDAO==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courseDAO, HttpStatus.CREATED);
    }*/

    @GetMapping("/public/{courseId}")
    public ResponseEntity<CourseDAO> getCourse(@PathVariable String courseId) {
        try {
            CourseDAO courseDAO = courseService.findCourseById(courseId);
            return new ResponseEntity<>(courseDAO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/public/all")
    public ResponseEntity<List<CourseDAO>> getAllCourses() {
        try {
            List<CourseDAO> courses = courseService.findAll();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/instructor/update/info/{courseId}")
    public ResponseEntity<?> updateBasicCourseInfo(@PathVariable String courseId, @RequestBody BasicCourseDTO basicCourseDTO) {
        try {
            CourseDAO course = courseService.updateBasicCourseInformation(courseId, basicCourseDTO.getName(), basicCourseDTO.getDescription(), basicCourseDTO.getPrice());
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("No Course available for the provided CourseID.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/instructor/add/{courseId}/lecture_note")
    public ResponseEntity<?> addLectureNotesToCourse(@PathVariable String courseId, @RequestParam MultipartFile file, @RequestParam String description, @RequestParam float weight) {
        try {
            LectureNote lectureNote = courseService.uploadLectureNote(courseId, file, description, weight);
            return new ResponseEntity<>(lectureNote, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/instructor/add/{courseId}/video")
    public ResponseEntity<?> addVideoToCourse(@PathVariable String courseId, @RequestParam MultipartFile file, @RequestParam String description, @RequestParam float weight) {
        try {
            Video courseVideo = courseService.uploadCourseVideo(courseId, file, description, weight);
            return new ResponseEntity<>(courseVideo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/instructor/add/{courseId}/quizzes")
    public ResponseEntity<?> addQuizzesToCourse(@PathVariable String courseId, @RequestBody List<Quiz> quizList) {
        try {
            List<Quiz> quizzes = courseService.addCourseQuizzes(courseId, quizList);
            return new ResponseEntity<>(quizzes, HttpStatus.CREATED);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/instructor/remove/quiz/{courseId}")
    public ResponseEntity<List<Quiz>> removeQuizFromCourse(@PathVariable String courseId, @RequestBody Quiz quiz) {
        try {
            List<Quiz> quizzes = courseService.removeQuiz(courseId, quiz);
            return new ResponseEntity<>(quizzes, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/instructor/remove/course/{courseId}")
    public ResponseEntity<?> removeCourse(@PathVariable String courseId) {
        try {
            courseService.removeCourse(courseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
