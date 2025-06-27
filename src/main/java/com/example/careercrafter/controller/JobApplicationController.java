package com.example.careercrafter.controller;

import com.example.careercrafter.model.JobApplication;
import com.example.careercrafter.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService applicationService;

    // ✅ Apply to a job (JWT required)
    @PostMapping("/apply/{jobId}")
    public ResponseEntity<JobApplication> apply(@PathVariable Long jobId, Authentication authentication) {
        String email = authentication.getName();  // Extract from JWT
        JobApplication app = applicationService.applyToJob(jobId, email);
        return ResponseEntity.ok(app);
    }

    // ✅ Get applications by logged-in user
    @GetMapping("/my")
    public ResponseEntity<List<JobApplication>> myApplications(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(applicationService.getApplicationsByUser(email));
    }

    // ✅ Employer: get applicants for a job (public for now, can be secured later)
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplication>> getByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsForJob(jobId));
    }
}

