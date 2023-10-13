package com.example.unimeeting.controller;

import com.example.unimeeting.dao.MeetingMapper;
import com.example.unimeeting.domain.MeetingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MeetingBoardController {

    @Autowired
    MeetingMapper metmap;

    // 사이드바 category
    @RequestMapping("/meeting/board")
    public ModelAndView viewMetBoard(String ctgr, String search){
        ModelAndView mv = new ModelAndView();
        List<String> category = metmap.viewCtgy();

        mv.addObject("ctgr", category.toString());

        List<MeetingDTO> meetings = metmap.viewMetBoard(ctgr,search);
        mv.addObject("met", meetings);

        mv.setViewName("testHTML");
        return mv;
    }
}
