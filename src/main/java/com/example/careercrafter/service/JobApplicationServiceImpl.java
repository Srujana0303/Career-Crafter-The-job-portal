package com.example.careercrafter.service;

import com.example.careercrafter.model.Job;
import com.example.careercrafter.model.JobApplication;
import com.example.careercrafter.repository.JobApplicationRepository;
import com.example.careercrafter.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Override
    public JobApplication applyToJob(Long jobId, String applicantEmail) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        JobApplication application = new JobApplication();
        application.setApplicantEmail(applicantEmail);
        application.setJob(job);

        return applicationRepository.save(application);
    }

    @Override
    public List<JobApplication> getApplicationsByUser(String email) {
        return applicationRepository.findByApplicantEmail(email);
    }

    @Override
    public List<JobApplication> getApplicationsForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }
}

