package com.example.unimeeting.controller;


import com.example.unimeeting.dao.MypageMapper;
import com.example.unimeeting.domain.MyInfoMeetingDTO;
import com.example.unimeeting.domain.UpdateUserVO;
import jakarta.servlet.http.HttpSession;
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

//  @ModelAttribute("user")
//  public UserVO sessionuser() {
//    return null;
//  }

  @ModelAttribute("user")
  public UserVO sessionuser() {
    return null;
  }

  @ModelAttribute("user")
  public UserVO updateSessionUser(UserVO userVO) {
    if(userVO != null) {
      System.out.println(userVO.getNickname());
      return userVO;
    }
    else
      return null;
  }

  @Autowired
  MypageMapper dao;

  /* Mypage 참여 목록 */
  @GetMapping("")
  public String myDefault(Model model, @ModelAttribute("user") UserVO s_user, HttpSession httpSession) {
    if(s_user == null){
      return "redirect:/userLogin.html";
    }
    model.addAttribute("list", dao.attendList(s_user));
    model.addAttribute("user", s_user);
    return "myPage";
  }

  /* Session 정보 Json 반환 함수*/
  @ResponseBody
  @GetMapping(value = "/getSessionData", produces = "application/json; charset=utf-8")
  public UserVO sessionUser(@ModelAttribute("user") UserVO s_user) {
    return s_user;
  }

  /* 참여, 스크랩, 생성한 미팅 목록 JSON 반환 함수*/
  @ResponseBody
  @GetMapping(value = "/{select}", produces = "application/json; charset=utf-8")
  public MyInfoMeetingDTO myInfoMeeting(@PathVariable String select,
      @ModelAttribute("user") UserVO s_user, Model model) {
    MyInfoMeetingDTO myInfoMeetingDTO = new MyInfoMeetingDTO();
    switch (select) {
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
    System.out.println(s_user);
    System.out.println(dao.attendList(s_user));
    System.out.println(dao.createList(s_user));
    System.out.println(dao.scrapList(s_user));
    return myInfoMeetingDTO;
  }

  /* 정보 변경 함수 */
  @PostMapping("/update")
  public ModelAndView updateUser(UserVO user, Model model, @ModelAttribute("user") UserVO userVO) {
    ModelAndView mav = new ModelAndView();
    boolean result = dao.updateUser(user);
    if (result) {
      model.addAttribute("msg", "정상적으로 변경되었습니다");
    } else {
      model.addAttribute("msg", "User 정보를 업데이트하는 동안 오류 발생했습니다. 다시 시도해 주세요");
    }

    userVO.setNickname(user.getNickname());
    userVO.setPassword(user.getPassword());

    RedirectView redirectView = new RedirectView("/mypage", true);
    mav.setView(redirectView);
//    mav.setViewName("myPage");
    return mav;
  }
}
