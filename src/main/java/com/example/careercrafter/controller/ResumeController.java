package com.example.careercrafter.controller;

import com.example.careercrafter.model.JobApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.example.careercrafter.repository.JobApplicationRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@RestController
@RequestMapping("/api/v1/resume")
public class ResumeController {
	private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);

    private final String uploadDir = "uploads/";

    @Autowired
    private JobApplicationRepository applicationRepo;

    @PostMapping(value = "/upload/{applicationId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadResume(@PathVariable Long applicationId,
                                               @RequestParam("file") MultipartFile file,
                                               Authentication authentication) {
        try {
        	logger.info("Resume upload requested for applicationId: {}", applicationId);
        	logger.info("Logged-in user: {}", authentication.getName());
        	logger.info("File uploaded: {}", file.getOriginalFilename());

            JobApplication application = applicationRepo.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            // Optional: Check if the logged-in user matches the one who applied
            String email = authentication.getName();
            if (!application.getApplicantEmail().equals(email)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed to upload resume for this application.");
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            application.setResumeFileName(fileName);
            applicationRepo.save(application);

            return ResponseEntity.ok("Resume uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

    @GetMapping("/download/{applicationId}")
    public void downloadResume(@PathVariable Long applicationId, HttpServletResponse response) {
        try {
            JobApplication application = applicationRepo.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            String fileName = application.getResumeFileName();
            if (fileName == null) throw new RuntimeException("No resume uploaded");

            Path filePath = Paths.get(uploadDir + fileName);
            if (!Files.exists(filePath)) throw new RuntimeException("File not found");

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            Files.copy(filePath, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            throw new RuntimeException("Download error: " + e.getMessage());
        }
    }
}
