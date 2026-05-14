package com.example.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dto.ChangePasswordRequest;
import com.example.backend.dto.ForgotPasswordRequest;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // REGISTER
    public String register(User user) {

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

    // LOGIN
    public String login(User user) {

        User existingUser = repo.findById(user.getUserId()).orElse(null);

        if(existingUser == null) {
            return "User not found";
        }

        if(existingUser.getPassword().equals(user.getPassword())) {
            return "Login Successful";
        }

        return "Invalid Password";
    }

    // CHANGE PASSWORD
    public String changePassword(ChangePasswordRequest request) {

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

    // GET USER QUESTIONS
    public User getUser(String id) {

        Optional<User> user = repo.findById(id);

        return user.orElse(null);
    }

    // FORGOT PASSWORD
    public String forgotPassword(ForgotPasswordRequest request) {

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