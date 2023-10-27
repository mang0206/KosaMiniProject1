package com.example.unimeeting.service;

import com.example.unimeeting.domain.UserDAO;
import com.example.unimeeting.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO UserDAO;

    @Autowired
    public UserServiceImpl(UserDAO UserDAO) {
        this.UserDAO = UserDAO;
    }

    @Override
    public UserVO getUserByUserId(String user_Id) {
        return UserDAO.getUserByUserId(user_Id);
    }

    @Override
    public void registerUser(UserVO user) {
        UserDAO.insertUser(user);
    }

    @Override
    public boolean authenticateUser(String user_id, String password) {
        UserVO user = UserDAO.getUserByUserId(user_id);
        return user != null && user.getPassword().equals(password);
    }
}
