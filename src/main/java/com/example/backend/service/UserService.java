package com.example.backend.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dto.ChangeCredentialsRequest;
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

        // PASSWORD VALIDATION
        String password = user.getPassword();

        String regex =
                "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$";

        if(!password.matches(regex)) {

            return "Password does not meet requirements";
        }

        // AGE VALIDATION (18+)
        Date currentDate = new Date();

        long diff =
                currentDate.getTime()
                - user.getDob().getTime();

        long age =
                diff / (1000L * 60 * 60 * 24 * 365);

        if(age < 18) {

            return "User must be 18+";
        }

        // PASSWORD CHANGE DATE
        user.setPasswordChangeDate(new Date());

        repo.save(user);

        return "Registration Successful";
    }

    // LOGIN
    public String login(User user) {

        User existingUser =
                repo.findById(
                        user.getUserId()
                ).orElse(null);

        if(existingUser == null) {

            return "User not found";
        }

        if(existingUser.getPassword()
                .equals(user.getPassword())) {

            Date currentDate = new Date();

            long diff =
                    currentDate.getTime()
                    - existingUser
                    .getPasswordChangeDate()
                    .getTime();

            long days =
                    diff / (1000 * 60 * 60 * 24);

            // LAST 15 DAYS WARNING
            if(days >= 75 && days < 90) {

                return "Warning: Your password will expire in "
                        + (90 - days)
                        + " days";
            }

            // PASSWORD EXPIRED
            if(days >= 90) {

                return "Password expired. Please change password";
            }

            return "Login Successful";
        }

        return "Invalid Password";
    }

    // CHANGE PASSWORD
    public String changePassword(
            ChangePasswordRequest request) {

        User existingUser =
                repo.findById(
                        request.getUserId()
                ).orElse(null);

        if(existingUser == null) {

            return "User not found";
        }

        if(!existingUser.getPassword()
                .equals(request.getOldPassword())) {

            return "Old password is incorrect";
        }

        String regex =
                "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$";

        if(!request.getNewPassword()
                .matches(regex)) {

            return "New password does not meet requirements";
        }

        if(!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

            return "New password and confirm password do not match";
        }

        existingUser.setPassword(
                request.getNewPassword()
        );

        // UPDATE PASSWORD CHANGE DATE
        existingUser.setPasswordChangeDate(
                new Date()
        );

        repo.save(existingUser);

        return "Password changed successfully";
    }

    // GET USER QUESTIONS
    public User getUser(String id) {

        Optional<User> user =
                repo.findById(id);

        return user.orElse(null);
    }

    // FORGOT PASSWORD
    public String forgotPassword(
            ForgotPasswordRequest request) {

        User existingUser =
                repo.findById(
                        request.getUserId()
                ).orElse(null);

        if(existingUser == null) {

            return "User not found";
        }

        boolean answer1Correct =
                existingUser.getAnswer1()
                .equals(request.getAnswer1());

        boolean answer2Correct =
                existingUser.getAnswer2()
                .equals(request.getAnswer2());

        // BOTH WRONG
        if(!answer1Correct && !answer2Correct) {

            return "Both answers are incorrect";
        }

        // ANSWER 1 WRONG
        if(!answer1Correct) {

            return "Answer 1 is incorrect";
        }

        // ANSWER 2 WRONG
        if(!answer2Correct) {

            return "Answer 2 is incorrect";
        }

        return "Your password is: "
                + existingUser.getPassword();
    }

    // CHANGE USER CREDENTIALS
    public String changeCredentials(
            ChangeCredentialsRequest request) {

        User existingUser =
                repo.findById(
                        request.getCurrentUserId()
                ).orElse(null);

        if(existingUser == null) {

            return "User not found";
        }

        // PASSWORD CHECK
        if(!existingUser.getPassword()
                .equals(request.getPassword())) {

            return "Incorrect password";
        }

        // DUPLICATE USER ID CHECK
        if(!request.getCurrentUserId()
                .equals(request.getNewUserId())
                &&
                repo.existsById(
                        request.getNewUserId()
                )) {

            return "New User ID already exists";
        }

        // UPDATE USER DETAILS
        existingUser.setUserId(
                request.getNewUserId()
        );

        existingUser.setQuestion1(
                request.getQuestion1()
        );

        existingUser.setAnswer1(
                request.getAnswer1()
        );

        existingUser.setQuestion2(
                request.getQuestion2()
        );

        existingUser.setAnswer2(
                request.getAnswer2()
        );

        repo.save(existingUser);

        return "Credentials updated successfully";
    }
}