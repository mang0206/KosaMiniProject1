package com.example.unimeeting.controller;

import com.example.unimeeting.dao.InfoMapper;
import com.example.unimeeting.domain.InfoDTO;
import com.example.unimeeting.domain.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

//import static jdk.internal.org.jline.utils.Colors.s;

@Controller
@SessionAttributes("user")
public class MainController {


    @Autowired
    InfoMapper dao;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class); //로그 기록 남기기 위해

    @ModelAttribute("user")
    public UserVO inout(){
        return null;
    }

    @GetMapping("/mainPage") //메인페이지 & 로그인/로그아웃 관리
    public String test(Model model, @ModelAttribute("user") UserVO id, @RequestParam(defaultValue = "no") String changeTest) {
        System.out.println("main");
        model.addAttribute("data", "hello");
        if (changeTest.equals("popular")){
            model.addAttribute("list", dao.popularSort());
        } else if (changeTest.equals("title")){
            model.addAttribute("list", dao.titleSort());
        } else if (changeTest.equals("start")) {
            model.addAttribute("list", dao.createDate());
        } else if (changeTest.equals("no")){
            model.addAttribute("list", dao.listM());
            System.out.println(dao.listM());
        }
        System.out.println(id);
        if (id != null){
            model.addAttribute("inout",id);
        }
        return "mainPage";
    }

    
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public void joinGet() {

        logger.info("회원가입 페이지");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void loginGet() {

        logger.info("로그인 페이지");
    }




    @GetMapping("/listSort")
    public ModelAndView sort() {
        ModelAndView mav = new ModelAndView();
        List<InfoDTO> sort = dao.titleSort();
        mav.addObject("sort", sort);
        mav.setViewName("mainPage");
        return mav;


    }

    @GetMapping("/search") //검색창
    public ModelAndView search(String keyword) {
        List<InfoDTO> list = dao.searchM1(keyword);
        ModelAndView mav = new ModelAndView();
        if (list.size() != 0) {
            mav.addObject("list", list);
            mav.addObject("button", "메인화면");
        } else {
            mav.addObject("msg", "추출x");
        }
        mav.setViewName("mainPage");
        return mav;
    }

    @GetMapping("/logout")
    public String logout(@ModelAttribute("user") UserVO id,WebRequest request, SessionStatus status, Model model){
        status.setComplete();
        request.removeAttribute("user", WebRequest.SCOPE_SESSION);
        System.out.println("logout");
        model.addAttribute("data", "hello");
        model.addAttribute("list", dao.listM());
        System.out.println(id);
        if (id != null){
            model.addAttribute("inout",id);
        }
        return "redirect:/mainPage";
    }

}



