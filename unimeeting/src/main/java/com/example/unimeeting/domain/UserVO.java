package com.example.unimeeting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserVO {
    private int idx;
    private String user_id;
    private String password;
    private String nickname;
    private String email;
    private String category;
    private String phone_number;

}
