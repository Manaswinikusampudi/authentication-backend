package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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