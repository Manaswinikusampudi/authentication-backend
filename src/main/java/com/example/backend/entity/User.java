package com.example.backend.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "DOB")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "QUESTION1")
    private String question1;

    @Column(name = "ANSWER1")
    private String answer1;

    @Column(name = "QUESTION2")
    private String question2;

    @Column(name = "ANSWER2")
    private String answer2;

    @Column(name = "PASSWORD_CHANGE_DATE")
    private Date passwordChangeDate;
}