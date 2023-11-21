package com.example.unimeeting.repository;

import com.example.unimeeting.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // 사용자 아이디로 사용자 찾기
    Optional<User> findByUserId(String user_id);

    // 닉네임으로 사용자 찾기
    Optional<User> findByNickname(String nickname);

    // 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);

    // 사용자 아이디와 비밀번호로 사용자 찾기
    Optional<User> findByUserIdAndPassword(String user_id, String password);

    // 사용자 아이디 또는 닉네임이 전달된 아규먼트 값으로 시작하는 사용자 리스트를 리턴하는 메서드
    List<User> findByUserIdStartingWithOrNicknameStartingWith(String user_id, String nickname);

    //










}