package com.example.unimeeting.controller;
import com.example.unimeeting.domain.UserVO;
import com.example.unimeeting.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserVO loginUser() {
        return new UserVO();
    }

    @PostMapping("/register")
    public String registerUser(UserVO user) {
        userService.registerUser(user);
        return "redirect:/mainPage";
    }


    @RequestMapping("/login")
    public String loginpage() {
        return "redirect:/userLogin.html";
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkUserId(@RequestParam("user_id") String userId) {
        boolean isAvailable = (userService.idcheck(userId) == null);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<Map<String, Boolean >> checkNickname(@RequestParam("nickname") String nickname) {
        boolean isAvailable = (userService.nicknamecheck(nickname) == null);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam("email") String email) {
        boolean isAvailable = (userService.emailcheck(email) == null);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/do")
    public String loginUser(@RequestParam("user_id") String user_id, @RequestParam("password") String password, Model model, HttpSession session) {
        if (userService.authenticateUser(user_id, password)) {
            UserVO user = userService.idcheck(user_id);
            session.setAttribute("user", user);
            return "mainPage";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "userLoginError";
        }
    }

}
