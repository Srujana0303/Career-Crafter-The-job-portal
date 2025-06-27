package com.example.careercrafter.repository;

import com.example.careercrafter.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByApplicantEmail(String applicantEmail);
    List<JobApplication> findByJobId(Long jobId);
}

