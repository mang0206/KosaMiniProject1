package com.example.unimeeting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class AddUserRequest {
    private String email;// 추가해야함 다른 것들
    private String password;
}
