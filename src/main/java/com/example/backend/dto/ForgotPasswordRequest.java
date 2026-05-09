package com.example.backend.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {

    private String userId;
    private String answer1;
    private String answer2;
}