package com.example.careercrafter.service;

import com.example.careercrafter.model.JobApplication;

import java.util.List;

public interface JobApplicationService {
    JobApplication applyToJob(Long jobId, String applicantEmail);
    List<JobApplication> getApplicationsByUser(String email);
    List<JobApplication> getApplicationsForJob(Long jobId);
}

