package com.example.unimeeting.service;

import com.example.unimeeting.dao.UserMapper;
import com.example.unimeeting.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper UserMapper;

    @Autowired
    public UserServiceImpl(UserMapper UserMapper) {
        this.UserMapper = UserMapper;
    }

    @Override
    public UserVO idcheck(String user_Id) {
        return UserMapper.idcheck(user_Id);
    }

    @Override
    public UserVO nicknamecheck(String nickname) {
        return UserMapper.nicknamecheck(nickname);
    }

    @Override
    public UserVO emailcheck(String email) {
        return UserMapper.emailcheck(email);
    }

    @Override
    public void registerUser(UserVO user) {
        UserMapper.insertUser(user);
    }

    @Override
    public boolean authenticateUser(String user_id, String password) {
        UserVO user = UserMapper.signIn(user_id);
        System.out.println(user != null && user.getPassword().equals(password));
        return user != null && user.getPassword().equals(password);
    }
}
