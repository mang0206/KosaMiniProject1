package com.example.unimeeting.controller;

import com.example.unimeeting.domain.UserVO;
import com.example.unimeeting.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserVO user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("user_id") String userId, @RequestParam("password") String password, Model model, HttpSession session) {
        if (userService.authenticateUser(userId, password)) {
            UserVO user = userService.getUserByUserId(userId);
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        UserVO user = (UserVO) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }
}
