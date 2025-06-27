package com.example.careercrafter.service;

import com.example.careercrafter.model.Job;
import java.util.List;

public interface JobService {
    Job postJob(Job job, String postedBy);
    List<Job> getAllJobs();
    Job getJobById(Long id);
    List<Job> searchJobs(String keyword);
}

