package com.example.careercrafter.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String companyName;
    private String location;

    private String postedBy;  // email of employer who posted it
}

