package com.example.unimeeting.controller;

import com.example.unimeeting.dao.CloudtypeTestMapper;
import com.example.unimeeting.dao.MypageMapper;
import com.example.unimeeting.domain.CloudtypeUserDTO;
import com.example.unimeeting.domain.CloudtypeUserVO;
import com.example.unimeeting.domain.MyInfoMeetingDTO;
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

@Controller
@SessionAttributes("user")
@RequestMapping("/mypage")
public class MypageSideController {
  //  세션 임시 지정
//  @Autowired
//  CloudtypeTestMapper dao_user;
  @ModelAttribute("user")
  public UserVO sessionuser() {
//    List<CloudtypeUserVO> list = dao_user.list();
//    CloudtypeUserVO login_user = null;
//    Iterator iter = list.iterator();
//    CloudtypeUserVO session_user = null;
//    while (iter.hasNext()) {
//      CloudtypeUserVO user = (CloudtypeUserVO)iter.next();
//      if (user.getUser_id().equals("user1")) {
//        session_user = user;
//      }
//    }
    return new UserVO();
  }

  @Autowired
  MypageMapper dao;

  @GetMapping("")
  public String myDefault(Model model, @ModelAttribute("user") UserVO s_user) {
    model.addAttribute("list", dao.attendList(s_user));
    return "myPage";
  }
}
//
//  @ResponseBody
//  @GetMapping(value = "/getSessionData", produces = "application/json; charset=utf-8")
//  public CloudtypeUserVO sessionUser(@ModelAttribute("user") CloudtypeUserVO s_user){
//    return s_user;
//  }
//
//  @ResponseBody
//  @GetMapping(value = "/{select}", produces = "application/json; charset=utf-8")
//  public MyInfoMeetingDTO myInfoMeeting(@PathVariable String select, @ModelAttribute("user") CloudtypeUserVO s_user, Model model) {
//    MyInfoMeetingDTO myInfoMeetingDTO = new MyInfoMeetingDTO();
//    switch(select){
//      case "attend":
//        myInfoMeetingDTO.setDivision(select);
//        myInfoMeetingDTO.setList(dao.attendList(s_user));
//        break;
//      case "create":
//        myInfoMeetingDTO.setDivision(select);
//        myInfoMeetingDTO.setList(dao.createList(s_user));
//        break;
//      case "scrap":
//        myInfoMeetingDTO.setDivision(select);
//        myInfoMeetingDTO.setList(dao.scrapList(s_user));
//        break;
//      case "myInfo":
//        model.addAttribute("info_user", s_user);
//        break;
//    }
//
//    return myInfoMeetingDTO;
//  }
//
//  @PostMapping("")
//  public void updateUser(@ModelAttribute("user") CloudtypeUserVO s_user, CloudtypeUserDTO user){
//    ModelAndView mav = new ModelAndView();
//    System.out.println(user);
//
//
//    if(user.getPassword().equals(s_user.getPassword())){
//
//    }
//  }
//}
