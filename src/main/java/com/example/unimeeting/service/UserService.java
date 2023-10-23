package com.example.unimeeting.service;

import com.example.unimeeting.domain.UserVO;

public interface UserService {
    UserVO getUserByUserId(String userId);
    void registerUser(UserVO user);
    boolean authenticateUser(String userId, String password);
}
