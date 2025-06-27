package com.example.careercrafter.service;

import com.example.careercrafter.model.User;

public interface UserService {
    User registerUser(User user);
    User getUserByEmail(String email);
}

