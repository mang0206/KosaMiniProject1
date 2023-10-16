package com.example.unimeeting.controller;

import com.example.unimeeting.dao.MeetingMapper;
import com.example.unimeeting.domain.MeetingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MeetingController {

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
        List<MeetingDTO> meetings = metmap.viewMetBoard(ctgr ,search!=null ? search.trim() : search, (page-1)*4);
        mv.addObject("met_list", meetings);

        mv.setViewName("MetBoardView");
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

    // view Meeting Post
    @GetMapping("/meeting/post")
    public ModelAndView viewMetPost(int idx){
        MeetingDTO meeting = metmap.viewMetPost(idx);
        int meeting_member = metmap.countMetMem(idx);
        ModelAndView mv = new ModelAndView();
        if(meeting != null){
            mv.addObject("meeting", meeting);
            mv.addObject("meeting_member", meeting_member);
        }
        mv.setViewName("MetPostView");
        return mv;
    }

    // delete meeting
    @RequestMapping("/meeting/deleteMet")
    public String deleteMetPost(int idx, String writer_nickname){
        System.out.println("test");
        return metmap.deleteMeeting(idx, writer_nickname) ? "redirect:/meeting/board" : "redirect:/";
    }

}
