package com.example.backend.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String userId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}