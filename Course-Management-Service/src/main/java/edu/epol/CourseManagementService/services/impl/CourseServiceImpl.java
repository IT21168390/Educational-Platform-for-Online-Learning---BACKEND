package edu.epol.CourseManagementService.services.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import edu.epol.CourseManagementService.consts.Status;
import edu.epol.CourseManagementService.models.Course;
import edu.epol.CourseManagementService.models.CourseContent;
import edu.epol.CourseManagementService.models.LectureNote;
import edu.epol.CourseManagementService.repositories.CourseRepository;
import edu.epol.CourseManagementService.services.CourseService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String connectionString;

    private BlobServiceClient blobServiceClient;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @PostConstruct
    public void init() {
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    public String uploadFile(MultipartFile file, String details) throws IOException {
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
        courseContent.setLecture_note(new LectureNote(null,fileURL, 25));

        course.setCourse_content(courseContent);

        courseRepository.save(course);

        return "File uploaded.";
    }
}
