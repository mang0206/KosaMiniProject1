package com.example.unimeeting.controller;

import com.example.unimeeting.dao.CloudtypeTestMapper;
import com.example.unimeeting.domain.UpdateUserVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CloudtypeController {
  @Autowired
  CloudtypeTestMapper dao;

  @RequestMapping("/test1")
  public ModelAndView list(){
    ModelAndView mav = new ModelAndView();
    List<UpdateUserVO> list = dao.list();

    mav.addObject("list", list);
    System.out.println(dao);
    mav.setViewName("cttest");

    return mav;
  }

}
