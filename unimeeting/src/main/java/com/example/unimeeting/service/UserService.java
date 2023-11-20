package com.example.unimeeting.service;

import com.example.unimeeting.domain.UserVO;

public interface    UserService {
    UserVO idcheck(String user_Id);
    UserVO nicknamecheck(String nickname);
    UserVO emailcheck(String email);

    void registerUser(UserVO user);
    boolean authenticateUser(String user_Id, String password);
}
