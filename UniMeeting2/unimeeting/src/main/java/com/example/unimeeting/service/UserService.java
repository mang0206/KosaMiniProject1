package com.example.unimeeting.service;

import lombok.RequiredArgsConstructor;
import com.example.unimeeting.domain.User;
import com.example.unimeeting.dto.AddUserRequest;
import com.example.unimeeting.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public int save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getIdx();
    }
}
