package com.example.unimeeting.controller;

import com.example.unimeeting.domain.User;
import com.example.unimeeting.repository.UserRepository;
import com.example.unimeeting.service.UserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserDetailService userDetailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;



    //아이디로 사용자 확인
//    @GetMapping("/user_id/{user_id}")
//    public ResponseEntity<User> getUserById(@PathVariable String user_id) {
//        Optional<User> user = userDetailService.findByUserId(user_id);
//        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//    @GetMapping("/user_id/{user_id}")
//    public ResponseEntity<User> getUserById(@PathVariable String user_id) {
//        Optional<User> user = userDetailService.findByUserId(user_id);
//        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//    }

    //아중첵 이게 맞음
    @GetMapping("/user_id/{user_id}")
    public ResponseEntity<String> checkUserId(@PathVariable String user_id) {
        Optional<User> user = userDetailService.findByUserId(user_id);
        return user.map(u -> ResponseEntity.ok("중복된 아이디입니다.")).orElse(ResponseEntity.ok("사용 가능한 아이디입니다."));
    }



    //닉네임으로 사용자 확인
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<String> checkUserNickname(@PathVariable String nickname){
        Optional<User> user = userDetailService.findByNickname(nickname);
        return user.map(u -> ResponseEntity.ok("중복된 닉네임입니다.")).orElse(ResponseEntity.ok("사용가능 닉네임입니다."));
    }
//    @GetMapping("/nickname/{nickname}")
//    public ResponseEntity<User> getUserByNickname(@PathVariable String nickname) {
//        Optional<User> user = userDetailService.findByNickname(nickname);
//        ResponseEntity<User> responseEntity;
//        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }

    //이메일로 사용자 확인
    @GetMapping("email/{email}")
    public ResponseEntity<String> checkUserEmail(@PathVariable String email){
        Optional<User> user = userDetailService.findByEmail(email);
        return user.map(u -> ResponseEntity.ok("중복된 이메일입니다.")).orElse(ResponseEntity.ok("사용가능 이메일입니다."));
    }
//    @GetMapping("/email/{email}")
//    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
//        Optional<User> user = userDetailService.findByEmail(email);
//        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
    //===============로그인=================//
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestParam String user_id, @RequestParam String password) {
        if (userDetailService.findByUserIdAndPassword(user_id, password).isPresent()) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.badRequest().body("아이디 비밀번호를 잘못입력");
        }
    }
    //아이디로 검색
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String user_id, @RequestParam String nickname) {
        List<User> users = userDetailService.findByUserIdStartingWithOrNicknameStartingWith(user_id, nickname);
        return ResponseEntity.ok(users);
    }

    //===============회원가입=============//
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        System.out.println(user);
        try {
            userDetailService.register(user.getUserId(), user.getPassword(),user.getNickname(),user.getEmail(),user.getCategory(),user.getPhoneNumber());
            return ResponseEntity.ok("가입 성공ㅋ");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("가입 실패");
        }
    }
    @PostMapping("/login")
    public String login() {
        return "토큰 발행 완료";
    }
}