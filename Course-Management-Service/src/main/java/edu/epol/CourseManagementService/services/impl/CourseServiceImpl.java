package edu.epol.CourseManagementService.services.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import edu.epol.CourseManagementService.consts.CourseContentWeights;
import edu.epol.CourseManagementService.converters.CourseDAOConverter;
import edu.epol.CourseManagementService.dao.CourseDAO;
import edu.epol.CourseManagementService.models.*;
import edu.epol.CourseManagementService.repositories.CourseRepository;
import edu.epol.CourseManagementService.services.CourseService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseDAOConverter courseDAOConverter;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;

    private BlobServiceClient blobServiceClient;

    public List<CourseDAO> findAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDAO> courseDAOList = new ArrayList<>();
        for (Course course:
             courses) {
            CourseDAO courseDAO = new CourseDAO();
            courseDAO.setId(course.getId());
            courseDAO.setName(course.getName());
            courseDAO.setCourse_content(course.getCourse_content());
            courseDAO.setPrice(course.getPrice());
            courseDAO.setStatus(course.getStatus());

            courseDAOList.add(courseDAO);
        }
        return courseDAOList;
    }

    public CourseDAO findCourseById(String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(NoSuchElementException::new);
        return new CourseDAO(course.getId(), course.getName(), course.getCourse_content(), course.getPrice(), course.getStatus());
    }

    @Override
    public CourseDAO createCourse(CourseDAO courseDAO) {
        try {
            Course newCourse = courseDAOConverter.convertCourseDAOToCourse(courseDAO);
            Course course = courseRepository.save(newCourse);
            return courseDAOConverter.convertCourseToCourseDAO(course);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public CourseDAO updateFullCourse(CourseDAO courseDAO) {
        Course course = courseRepository.findById(courseDAO.getId()).orElseThrow(NoSuchElementException::new);
        course.setName(courseDAO.getName());
        course.setCourse_content(courseDAO.getCourse_content());
        course.setPrice(courseDAO.getPrice());
        course.setStatus(courseDAO.getStatus());

        Course updatedCourse = courseRepository.save(course);
        return courseDAOConverter.convertCourseToCourseDAO(updatedCourse);
    }

    @Override
    public CourseDAO updateBasicCourseInformation(String courseId, String courseName, double price) {
        Course course = courseRepository.findById(courseId).orElseThrow(NoSuchElementException::new);
        course.setName(courseName);
        course.setPrice(price);

        Course updatedCourse = courseRepository.save(course);
        CourseDAO courseDAO = new CourseDAO(updatedCourse.getId(), updatedCourse.getName(), updatedCourse.getCourse_content(), updatedCourse.getPrice(), updatedCourse.getStatus());
        return courseDAO;
    }

    @PostConstruct
    public void init() {
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    /*public String uploadFile(MultipartFile file, String details) throws IOException {
        String blobFileName = file.getOriginalFilename();
        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(blobFileName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        String fileURL = blobClient.getBlobUrl();

        Course course = new Course();
        course.setName("Test");
        course.setStatus(Status.PENDING);
        course.setPrice(100);

        CourseContent courseContent = new CourseContent();
        courseContent.setLecture_note(new LectureNote(null,fileURL, 25, ""));

        course.setCourse_content(courseContent);

        courseRepository.save(course);

        return "File uploaded.";
    }*/

    private String uploadCourseContent(MultipartFile file, String description, float weight) throws IOException {
        String blobFileName = file.getOriginalFilename();
        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(blobFileName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobClient.getBlobUrl();
    }

    public LectureNote uploadLectureNote(String courseId, MultipartFile file, String description, float weight){
        try {
            String fileURL = uploadCourseContent(file, description, weight);
            Course course = courseRepository.findById(courseId).get();

            CourseContent courseContent = course.getCourse_content();

            LectureNote lectureNote = courseContent.getLecture_note();
            lectureNote.setNote_Url(fileURL);
            lectureNote.setWeight(weight);
            lectureNote.setDescription(description);

            courseContent.setLecture_note(lectureNote);
            course.setCourse_content(courseContent);
            Course updatedCourse = courseRepository.save(course);
            return updatedCourse.getCourse_content().getLecture_note();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Video uploadCourseVideo(String courseId, MultipartFile file, String description, float weight){
        try {
            String fileURL = uploadCourseContent(file, description, weight);
            Course course = courseRepository.findById(courseId).get();
            CourseContent courseContent = course.getCourse_content();

            Video courseVideo = courseContent.getVideo();
            courseVideo.setVideo_Url(fileURL);
            courseVideo.setWeight(weight);
            courseVideo.setDescription(description);

            courseContent.setVideo(courseVideo);
            course.setCourse_content(courseContent);
            Course updatedCourse = courseRepository.save(course);
            return updatedCourse.getCourse_content().getVideo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  List<Quiz> addCourseQuizzes(String courseId, List<Quiz> quizList) throws IOException {
        Course course = courseRepository.findById(courseId).orElseThrow(NoSuchElementException::new);
        List<Quiz> quizzes = course.getCourse_content().getQuizzes();

        int quizzesCount = 0;

        float currentTotalWeight = 0;
        if (!quizzes.isEmpty()){
            for (Quiz quiz: quizzes) {
                currentTotalWeight += quiz.getWeight();
                quizzesCount++;
            }
        }

        float newQuizzesTotalWeight = 0;
        String quizWithIssues = "";
        boolean validAnswers = true;
        if (!quizList.isEmpty()){
            for (Quiz quiz: quizList) {
                newQuizzesTotalWeight += quiz.getWeight();
                quizzesCount++;

                /*String[] quizOptions = quiz.getOptions();
                ArrayList<String> quizOptionsList = new ArrayList<>());
                quizOptionsList.addAll(Arrays.asList(quizOptions));*/
                ArrayList<String> quizOptions = new ArrayList<>(Arrays.asList(quiz.getOptions()));

                if (!quizOptions.contains(quiz.getAnswer())) {
                    validAnswers = false;
                    quizWithIssues = quiz.getQuestion();
                }
            }
        }

        if (currentTotalWeight+newQuizzesTotalWeight > CourseContentWeights.COURSE_QUIZZES_WEIGHT) {
            throw new IOException("Quizzes weights exceed the allowed maximum amount!");
        } else if (!validAnswers) {
            throw new IOException("Quiz ("+quizWithIssues+")'s answer is not included in options!");
        }

        CourseContent courseContent = course.getCourse_content();

        if (!quizList.isEmpty()){
            for (Quiz quiz: quizList) {
                quizzes.add(quiz);
            }
            courseContent.setQuizzes(quizzes);
        }

        if ((currentTotalWeight+newQuizzesTotalWeight) < CourseContentWeights.COURSE_QUIZZES_WEIGHT) {
            float weightForEachQuiz = CourseContentWeights.COURSE_QUIZZES_WEIGHT / quizzesCount;
            for (Quiz quiz: courseContent.getQuizzes()) {
                quiz.setWeight(weightForEachQuiz);
            }
        }

        return courseRepository.save(course).getCourse_content().getQuizzes();
    }

    @Override
    public List<Quiz> removeQuiz(String courseId, Quiz quiz) {
        Course course = courseRepository.findById(courseId).get();
        CourseContent courseContent = course.getCourse_content();
        List<Quiz> quizList = courseContent.getQuizzes();
        quizList.remove(quiz);

        courseContent.setQuizzes(quizList);
        course.setCourse_content(courseContent);
        Course modifiedCourse = courseRepository.save(course);
        return modifiedCourse.getCourse_content().getQuizzes();
    }

    public void removeCourse(String courseId) {
            courseRepository.deleteById(courseId);
    }

}
