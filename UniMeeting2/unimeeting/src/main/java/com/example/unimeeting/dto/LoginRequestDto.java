package com.example.unimeeting.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String userId;
    private String password;
}