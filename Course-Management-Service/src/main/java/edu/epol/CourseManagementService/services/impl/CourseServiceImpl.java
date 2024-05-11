package edu.epol.CourseManagementService.services.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import edu.epol.CourseManagementService.consts.CourseContentConsts;
import edu.epol.CourseManagementService.consts.CourseContentWeights;
import edu.epol.CourseManagementService.consts.Status;
import edu.epol.CourseManagementService.converters.CourseDAOConverter;
import edu.epol.CourseManagementService.dao.BasicCourseDTO;
import edu.epol.CourseManagementService.dao.CourseDAO;
import edu.epol.CourseManagementService.models.*;
import edu.epol.CourseManagementService.repositories.CourseRepository;
import edu.epol.CourseManagementService.services.CourseService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
            courseDAO.setDescription(course.getDescription());
            courseDAO.setThumbnail(course.getThumbnail());
            courseDAO.setPrice(course.getPrice());
            courseDAO.setStatus(course.getStatus());

            courseDAOList.add(courseDAO);
        }
        return courseDAOList;
    }

    @Override
    public List<CourseDAO> findCoursesByStatus(Status status) {
        try {
            List<Course> courses = courseRepository.findAllByStatus(status);
            List<CourseDAO> courseDAOList = new ArrayList<>();
            for (Course course: courses) {
                CourseDAO courseDAO = courseDAOConverter.convertCourseToCourseDAO(course);
                courseDAOList.add(courseDAO);
            }
            return courseDAOList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public CourseDAO findCourseById(String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(NoSuchElementException::new);
        return new CourseDAO(course.getId(), course.getName(), course.getCourse_content(), course.getThumbnail(), course.getDescription(), course.getPrice(), course.getStatus());
    }

    private String uploadCourseThumbnail(MultipartFile file, String id) throws IOException {
        String blobFileName = id+"_Thumbnail_";//.concat(Objects.requireNonNull(file.getOriginalFilename()));

        if (file.getOriginalFilename()!=null && file.getOriginalFilename().contains(".")) {
            String extenson = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            blobFileName = blobFileName.concat("."+extenson);
        }

        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(blobFileName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobClient.getBlobUrl();
    }
    @Override
    public CourseDAO createCourse(BasicCourseDTO basicCourseDTO) {
        try {
            Course newCourse = new Course();

            newCourse.setName(basicCourseDTO.getName());
            newCourse.setPrice(basicCourseDTO.getPrice());
            newCourse.setCourse_content(new CourseContent(new LectureNote(), new Video(), new ArrayList<>()));
            newCourse.setStatus(Status.DRAFT);
            newCourse.setDescription(basicCourseDTO.getDescription());

            Course savedCourse = courseRepository.save(newCourse);

            String thumbnailURL = uploadCourseThumbnail(basicCourseDTO.getThumbnail(), savedCourse.getId());
            savedCourse.setThumbnail(thumbnailURL);
            Course course = courseRepository.save(savedCourse);

            return courseDAOConverter.convertCourseToCourseDAO(course);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /*@Override
    public CourseDAO createCourse(CourseDAO courseDAO) {
        try {
            Course newCourse = courseDAOConverter.convertCourseDAOToCourse(courseDAO);
            newCourse.setCourse_content(new CourseContent(new LectureNote(), new Video(), new ArrayList<>()));
            newCourse.setStatus(Status.PENDING);
            Course course = courseRepository.save(newCourse);
            return courseDAOConverter.convertCourseToCourseDAO(course);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }*/

    @Override
    public CourseDAO updateFullCourse(CourseDAO courseDAO) {
        Course course = courseRepository.findById(courseDAO.getId()).orElseThrow(NoSuchElementException::new);
        course.setName(courseDAO.getName());
        course.setCourse_content(courseDAO.getCourse_content());
        course.setPrice(courseDAO.getPrice());
        course.setStatus(courseDAO.getStatus());

        course.setDescription(courseDAO.getDescription());
        course.setThumbnail(courseDAO.getThumbnail());

        Course updatedCourse = courseRepository.save(course);
        return courseDAOConverter.convertCourseToCourseDAO(updatedCourse);
    }

    @Override
    public CourseDAO updateBasicCourseInformation(String courseId, String courseName, String description, double price) {
        Course course = courseRepository.findById(courseId).orElseThrow(NoSuchElementException::new);
        course.setName(courseName);
        course.setDescription(description);
        course.setPrice(price);

        Course updatedCourse = courseRepository.save(course);
        CourseDAO courseDAO = new CourseDAO(updatedCourse.getId(), updatedCourse.getName(), updatedCourse.getCourse_content(), updatedCourse.getThumbnail(), updatedCourse.getDescription(), updatedCourse.getPrice(), updatedCourse.getStatus());
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

    private String uploadCourseContent(MultipartFile file, String courseId, CourseContentConsts courseContentClarification) throws IOException {
        String blobFileName = courseId +"_"+ courseContentClarification.toString(); // file.getOriginalFilename();

        if (file.getOriginalFilename()!=null && file.getOriginalFilename().contains(".")) {
            String extenson = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            blobFileName = blobFileName.concat("."+extenson);
        }

        System.out.println("File upload initiated for: "+file.getContentType());

        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(blobFileName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobClient.getBlobUrl();
    }

    public LectureNote uploadLectureNote(String courseId, MultipartFile file, String description, float weight){
        try {
            Course course = courseRepository.findById(courseId).get();

            CourseContent courseContent = course.getCourse_content();

            String fileURL = uploadCourseContent(file, course.getId(), CourseContentConsts.LectureNote);

            LectureNote lectureNote = courseContent.getLecture_note();
            lectureNote.setNote_Url(fileURL);
            lectureNote.setWeight(weight);
            lectureNote.setDescription(description);

            courseContent.setLecture_note(lectureNote);
            course.setCourse_content(courseContent);

            if (course.getStatus().equals(Status.DRAFT)) {
                if (course.getCourse_content().getVideo().getVideo_Url()!=null && course.getCourse_content().getLecture_note().getNote_Url()!=null) {
                    course.setStatus(Status.PENDING);
                }
            }

            Course updatedCourse = courseRepository.save(course);
            return updatedCourse.getCourse_content().getLecture_note();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Video uploadCourseVideo(String courseId, MultipartFile file, String description, float weight){
        try {
            Course course = courseRepository.findById(courseId).get();
            CourseContent courseContent = course.getCourse_content();

            String fileURL = uploadCourseContent(file, course.getId(), CourseContentConsts.Video);

            Video courseVideo = courseContent.getVideo();
            courseVideo.setVideo_Url(fileURL);
            courseVideo.setWeight(weight);
            courseVideo.setDescription(description);

            courseContent.setVideo(courseVideo);
            course.setCourse_content(courseContent);

            if (course.getStatus().equals(Status.DRAFT)) {
                if (course.getCourse_content().getVideo().getVideo_Url()!=null && course.getCourse_content().getLecture_note().getNote_Url()!=null) {
                    course.setStatus(Status.PENDING);
                }
            }

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

        /*float currentTotalWeight = 0;
        if (!quizzes.isEmpty()){
            for (Quiz quiz: quizzes) {
                currentTotalWeight += quiz.getWeight();
                quizzesCount++;
            }
        }*/

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

        if (/*currentTotalWeight+*/newQuizzesTotalWeight > CourseContentWeights.COURSE_QUIZZES_WEIGHT) {
            throw new IOException("Quizzes weights exceed the allowed maximum amount!");
        } else if (!validAnswers) {
            throw new IOException("Quiz ("+quizWithIssues+")'s answer is not included in options!");
        }

        CourseContent courseContent = course.getCourse_content();

        if (!quizList.isEmpty()){
            quizzes.clear();
            for (Quiz quiz: quizList) {
                if (quiz.getId().isEmpty() || quiz.getId().isBlank() || quiz.getId() == null) {
                    quiz.setId(UUID.randomUUID().toString() + Long.toHexString(System.currentTimeMillis()));
                }
                quizzes.add(quiz);
            }
            courseContent.setQuizzes(quizzes);
        }

        if ((/*currentTotalWeight*/+newQuizzesTotalWeight) < CourseContentWeights.COURSE_QUIZZES_WEIGHT) {
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
        try {
            Course course = courseRepository.findById(courseId).get();
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

            for (BlobItem blobItem : blobContainerClient.listBlobsByHierarchy(course.getId())) {
                String blobName = blobItem.getName();
                // Check whether the blob name starts with the specified id or not
                if (blobName.startsWith(course.getId())) {
                    blobContainerClient.getBlobClient(blobName).delete();
                    System.out.println("File "+blobName+" deleted from the Azure storage.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Course "+courseId+" has been removed from the database.");
            courseRepository.deleteById(courseId);
    }

}
