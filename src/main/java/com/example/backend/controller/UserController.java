package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.ChangeCredentialsRequest;
import com.example.backend.dto.ChangePasswordRequest;
import com.example.backend.dto.ForgotPasswordRequest;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService service;

    // REGISTER API
    @PostMapping("/register")
    public String register(
            @RequestBody User user) {

        return service.register(user);
    }

    // LOGIN API
    @PostMapping("/login")
    public String login(
            @RequestBody User user) {

        return service.login(user);
    }

    // CHANGE PASSWORD API
    @PostMapping("/changePassword")
    public String changePassword(
            @RequestBody ChangePasswordRequest request) {

        return service.changePassword(request);
    }

    // GET USER QUESTIONS API
    @GetMapping("/user/{id}")
    public User getUser(
            @PathVariable("id") String id) {

        return service.getUser(id);
    }

    // FORGOT PASSWORD API
    @PostMapping("/forgotPassword")
    public String forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        return service.forgotPassword(request);
    }

    // CHANGE USER CREDENTIALS API
    @PostMapping("/changeCredentials")
    public String changeCredentials(
            @RequestBody ChangeCredentialsRequest request) {

        return service.changeCredentials(request);
    }
}