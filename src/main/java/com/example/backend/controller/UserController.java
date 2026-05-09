package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.ChangePasswordRequest;
import com.example.backend.dto.ForgotPasswordRequest;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository repo;

    // REGISTER API
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if(repo.existsById(user.getUserId())) {
            return "User already exists";
        }

        String password = user.getPassword();

        String regex =
                "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$";

        if(!password.matches(regex)) {

            return "Password does not meet requirements";
        }

        repo.save(user);

        return "Registration Successful";
    }

    // LOGIN API
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existingUser = repo.findById(user.getUserId()).orElse(null);

        if(existingUser == null) {
            return "User not found";
        }

        if(existingUser.getPassword().equals(user.getPassword())) {
            return "Login Successful";
        }

        return "Invalid Password";
    }

    // CHANGE PASSWORD API
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ChangePasswordRequest request) {

        User existingUser = repo.findById(request.getUserId()).orElse(null);

        if(existingUser == null) {
            return "User not found";
        }

        if(!existingUser.getPassword().equals(request.getOldPassword())) {
            return "Old password is incorrect";
        }

        String regex =
                "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$";

        if(!request.getNewPassword().matches(regex)) {

            return "New password does not meet requirements";
        }

        if(!request.getNewPassword().equals(request.getConfirmPassword())) {
            return "New password and confirm password do not match";
        }

        existingUser.setPassword(request.getNewPassword());

        repo.save(existingUser);

        return "Password changed successfully";
    }

    // FORGOT PASSWORD API
    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) {

        User existingUser = repo.findById(request.getUserId()).orElse(null);

        if(existingUser == null) {
            return "User not found";
        }

        if(existingUser.getAnswer1().equals(request.getAnswer1())
                && existingUser.getAnswer2().equals(request.getAnswer2())) {

            return "Your password is: " + existingUser.getPassword();
        }

        return "Your answers are incorrect";
    }
}