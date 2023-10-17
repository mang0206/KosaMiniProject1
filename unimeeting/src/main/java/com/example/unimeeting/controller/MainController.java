package com.example.unimeeting.controller;

import com.example.unimeeting.dao.InfoMapper;
import com.example.unimeeting.domain.InfoDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;

import java.util.List;

@Controller
@SessionAttributes("user")

public class MainController {

    @Autowired
    InfoMapper dao;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class); //로그 기록 남기기 위해


    @GetMapping("/testPage")
    public String test(Model model) {
        model.addAttribute("data", "hello");
        model.addAttribute("list", dao.listM());


        return "testPage";
    }
    
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public void joinGet() {

        logger.info("회원가입 페이지");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void loginGet() {

        logger.info("로그인 페이지");
    }

    @GetMapping("/list") //소모임 리스트
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        List<InfoDTO> list = dao.listM();
        mav.addObject("list", list);
        mav.setViewName("testPage");
        return mav;

    }

    @GetMapping("/meeting/search") //검색창
    public ModelAndView search(String keyword) {
        List<InfoDTO> list = dao.searchM1(keyword);
        ModelAndView mav = new ModelAndView();
        if (list.size() != 0) {
            mav.addObject("listSearch", list);
            mav.addObject("button", "메인화면");
        } else {
            mav.addObject("msg", "추출x");
        }
        mav.setViewName("testPage");
        return mav;
    }
    @GetMapping(value="index")

    public void indexGET()(ModelAttribute() request) {
        @ModelAttribute("login")
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();   // 세션 날림
        }
        model.addAttribute("loginout", "123");
        return "redirect:/";
    }

}



