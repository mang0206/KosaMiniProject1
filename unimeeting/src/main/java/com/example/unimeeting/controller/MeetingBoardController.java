package com.example.unimeeting.controller;

import com.example.unimeeting.dao.MeetingMapper;
import com.example.unimeeting.domain.MeetingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class MeetingBoardController {

    @Autowired
    MeetingMapper metmap;

    // Get Category
    @RequestMapping(value = "/getCategory", produces = "application/json; charset=utf-8" )
    @ResponseBody
    public List<String> getCategory(){
        return metmap.viewCtgy();
    }


    // Meeting Board
    // ctgr(category), path -> default all
    // search query
        @RequestMapping(value = {"/meeting/board" ,"/meeting/board/{ctgr}"})
        public ModelAndView viewMetBoard(@PathVariable(required = false) String ctgr,@RequestParam(required = false) String search, @RequestParam(defaultValue = "1") int page){
        ModelAndView mv = new ModelAndView();
        mv.addObject("ctgr_list", getCategory());
        List<MeetingDTO> meetings = metmap.viewMetBoard(ctgr ,search, (page-1)*4);
        mv.addObject("met_list", meetings);

        mv.setViewName("testHTML");
        return mv;
    }

    // Insert Meeting Post
    @PostMapping("/meeting/insertMet")
    public String insertMet(MeetingDTO meetingDTO, Model m){
        boolean result = metmap.insertMet(meetingDTO);
        if (!result) {
            m.addAttribute("msg", "글 작성을 처리하는 동안 오류 발생");
        }
        return "redirect:/meeting/board";
    }

}
