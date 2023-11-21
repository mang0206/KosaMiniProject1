package com.example.unimeeting;

import com.example.unimeeting.domain.User;
import com.example.unimeeting.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach()
    void pr() {
        System.out.println("=".repeat(80));
    }

    @Test
    @Order(1)
    public void findByUserId() {
        Optional<User> user = userRepository.findByUserId("hapal");
        System.out.println(user.isEmpty() ? "#####유저 아이디 정보가 존재하지 않습니다.#####" : user.get());
    }

//    @Test
//    @Order(2)
//    public void findByNickname() {
//        User user = userRepository.findByNickname("hapal");
//        System.out.println(user.isEmpty() ? "#####유저 닉네임 정보가 존재하지 않습니다.#####" : user.get());
//    }

    @Test
    @Order(3)
    public void findByEmail() {
        Optional<User> user = userRepository.findByEmail("hapal");
        System.out.println(user.isEmpty() ? "##### 정보가 존재하지 않습니다.#####" : user.get());
    }

    @Test
    @Order(4)
    public void findByUserIdAndPassword() {
        Optional<User> user = userRepository.findByUserIdAndPassword("hapal", "1234");
        System.out.println(user.isEmpty() ? "##### 정보가 존재하지 않습니다.#####" : user.get());
    }

    @Test
    @Order(5)
    public void findByUserIdStartingWithOrNicknameStartingWith() {
        List<User> user = userRepository.findByUserIdStartingWithOrNicknameStartingWith("hapal", "1234");
        user.stream().forEach(System.out::println);
    }

    @Test
    @Order(6)
    @Transactional
    void update(){
        try {
            User user = userRepository.findById(53).get();
            user.setNickname("맹꽁");
            user.setUserId("maeng");
        } catch(Exception e) {
            System.out.println("수정 실패");
            return;
        }
        System.out.println(userRepository.findById(53).get());
    }

    @Test
    @Order(7)
    @Transactional
    void delete(){
        try {
            userRepository.deleteById(53);
        } catch(Exception e) {
            System.out.println("삭제 실패");
        }
        System.out.println(userRepository.findById(5));
    }

}
