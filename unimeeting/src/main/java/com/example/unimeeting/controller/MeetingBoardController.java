package com.example.unimeeting.controller;

import com.example.unimeeting.dao.MeetingMapper;
import com.example.unimeeting.domain.MeetingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MeetingBoardController {

    @Autowired
    MeetingMapper metmap;

    // Meeting Board
    // ctgr(category), path -> default all
    // search query
        @RequestMapping(value = {"/meeting/board" ,"/meeting/board/{ctgr}"})
        public ModelAndView viewMetBoard(@PathVariable(required = false) String ctgr,@RequestParam(required = false) String search, @RequestParam(defaultValue = "1") int page){
        ModelAndView mv = new ModelAndView();
        List<String> category = metmap.viewCtgy();
        mv.addObject("ctgr_list", category);
        List<MeetingDTO> meetings = metmap.viewMetBoard(ctgr ,search, (page-1)*4);
        mv.addObject("met_list", meetings);

        mv.setViewName("testHTML");
        return mv;
    }
}
