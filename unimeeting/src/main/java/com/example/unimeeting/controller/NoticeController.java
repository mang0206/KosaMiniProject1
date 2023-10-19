package com.example.unimeeting.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.unimeeting.dao.NoticeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.unimeeting.domain.NoticeVO;
import com.example.unimeeting.domain.UserVO;

@Controller
@RequestMapping("/notice")
@SessionAttributes("user")

public class NoticeController {
    @ModelAttribute("user")
    public UserVO sessionLogin() {
        return null;
    }

    private static final Logger logger =
            LoggerFactory.getLogger(
                    NoticeController.class);
    @Autowired
    NoticeMapper noticeMapper;

    //=======공지 게시판 글 목록=========//
    @RequestMapping("/list")
    public ModelAndView process() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("noticeList");
        mav.addObject("list", noticeMapper.selectList());
        return mav;
    }
    // ======공지 글 상세페이지 ======//
    @RequestMapping("/detail")
    public ModelAndView viewDetail(int idx) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("noticeDetail");
        mav.addObject("detail", noticeMapper.selectNotice(idx));
        return mav;
    }
    //========공지 작성=================//
    @RequestMapping("/write")
    public String write(NoticeVO noticeVO,@ModelAttribute("user") UserVO user){
        noticeVO.setType("1");
        noticeVO.setWriter_nickname(user.getNickname());
        noticeMapper.insertNotice(noticeVO);
        return "redirect:/notice/list";
    }

    @RequestMapping("/delete")
    public String deleteNotice(int idx,@ModelAttribute("user") UserVO user){

        noticeMapper.deleteNotice(idx, user.getNickname());
        return "redirect:/notice/list";
    }







}
