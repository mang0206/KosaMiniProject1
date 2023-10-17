package com.example.unimeeting.controller;


import com.example.unimeeting.dao.MypageMapper;
import com.example.unimeeting.domain.MyInfoMeetingDTO;
import com.example.unimeeting.domain.UpdateUserVO;
import java.util.Iterator;
import java.util.List;

import com.example.unimeeting.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes("user")
@RequestMapping("/mypage")
public class MypageSideController {

  @ModelAttribute("user")
  public UserVO sessionuser() {
    return null;
  }
  @Autowired
  MypageMapper dao;

  @GetMapping("")
  public String myDefault(Model model, @ModelAttribute("user") UserVO s_user) {
    if(s_user == null){
      return "redirect:/userLogin.html";
    }
    model.addAttribute("list", dao.attendList(s_user));
    model.addAttribute("user",s_user);
    return "myPage";
  }

  @ResponseBody
  @GetMapping(value = "/getSessionData", produces = "application/json; charset=utf-8")
  public UserVO sessionUser(@ModelAttribute("user") UserVO s_user){
    return s_user;
  }

  @ResponseBody
  @GetMapping(value = "/{select}", produces = "application/json; charset=utf-8")
  public MyInfoMeetingDTO myInfoMeeting(@PathVariable String select, @ModelAttribute("user") UserVO s_user, Model model) {
    MyInfoMeetingDTO myInfoMeetingDTO = new MyInfoMeetingDTO();
    switch(select){
      case "attend":
        myInfoMeetingDTO.setDivision(select);
        myInfoMeetingDTO.setList(dao.attendList(s_user));
        break;
      case "create":
        myInfoMeetingDTO.setDivision(select);
        myInfoMeetingDTO.setList(dao.createList(s_user));
        break;
      case "scrap":
        myInfoMeetingDTO.setDivision(select);
        myInfoMeetingDTO.setList(dao.scrapList(s_user));
        break;
      case "myInfo":
        model.addAttribute("info_user", s_user);
        break;
    }

    return myInfoMeetingDTO;
  }

  @PostMapping("/update")
  public ModelAndView updateUser(UserVO user, Model model){
    ModelAndView mav = new ModelAndView();
    System.out.println(user);
    boolean result = dao.updateUser(user);
    if(result){
      model.addAttribute("msg", "정상적으로 변경되었습니다");
    } else {
      model.addAttribute("msg", "User 정보를 업데이트하는 동안 오류 발생했습니다. 다시 시도해 주세요");
    }

    RedirectView redirectView = new RedirectView("/mypage", true);
    mav.setView(redirectView);
//    mav.setViewName("myPage");
    return mav;
  }
}
