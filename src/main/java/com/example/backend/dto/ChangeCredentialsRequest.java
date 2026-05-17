package com.example.backend.dto;

import lombok.Data;

@Data
public class ChangeCredentialsRequest {

    private String currentUserId;

    private String password;

    private String newUserId;

    private String question1;
    private String answer1;

    private String question2;
    private String answer2;
}