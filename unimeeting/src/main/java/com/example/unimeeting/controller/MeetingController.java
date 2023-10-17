package com.example.unimeeting.controller;

import com.example.unimeeting.dao.MeetingMapper;
import com.example.unimeeting.dao.MeetingMemberMapper;
import com.example.unimeeting.dao.ScrapMapper;
import com.example.unimeeting.domain.MeetingDTO;
import com.example.unimeeting.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/meeting")
@SessionAttributes("user")
public class MeetingController {

    @Autowired
    MeetingMapper meetingMapper;

    @Autowired
    MeetingMemberMapper meetingMemberMapper;

    @Autowired
    ScrapMapper scrapMapper;


    // Get Category
    @RequestMapping(value = "/getCategory", produces = "application/json; charset=utf-8" )
    @ResponseBody
    public List<String> getCategory(){
        return meetingMapper.viewCtgy();
    }


    // Meeting Board
    // ctgr(category), path -> default all
    // search query
        @RequestMapping(value = {"" ,"/{ctgr}"})
        public ModelAndView viewMetBoard(@PathVariable(required = false) String ctgr,@RequestParam(required = false) String search, @RequestParam(defaultValue = "1") int page){
        ModelAndView mv = new ModelAndView();
        mv.addObject("ctgr_list", getCategory());
        List<MeetingDTO> meetings = meetingMapper.viewMetBoard(ctgr ,search!=null ? search.trim() : search, (page-1)*4);
        mv.addObject("met_list", meetings);

        mv.setViewName("MetBoardView");
        return mv;
    }

    // Insert Meeting Post
    @PostMapping("/insertMet")
    public String insertMet(MeetingDTO meetingDTO, Model m){
        boolean result = meetingMapper.insertMet(meetingDTO);
        if (!result) {
            m.addAttribute("msg", "글 작성을 처리하는 동안 오류 발생");
        }
        return "redirect:/meeting/board";
    }

    // view Meeting Post
    @GetMapping("/post")
    public ModelAndView viewMetPost(int idx){
        MeetingDTO meeting = meetingMapper.viewMetPost(idx);
        int meeting_member = meetingMapper.countMetMem(idx);
        ModelAndView mv = new ModelAndView();
        if(meeting != null){
            mv.addObject("meeting", meeting);
            mv.addObject("meeting_member", meeting_member);
        }
        mv.setViewName("MetPostView");
        return mv;
    }

    // get Meeting
    @RequestMapping(value = "/getMetJson", produces = "application/json; charset=utf-8")
    @ResponseBody
    public MeetingDTO getMetJson(int idx){
        return meetingMapper.viewMetPost(idx);
    }

    // delete meeting
    @RequestMapping("/deleteMet")
    public String deleteMetPost(int idx, String writer_nickname){ // HttpSession
        return meetingMapper.deleteMeeting(idx, writer_nickname) ? "redirect:/meeting/board" : "redirect:/";
    }

    // update meeting
    @RequestMapping("/updateMet")
    public String updateMetPost(MeetingDTO meetingDTO,Model m){
        System.out.println(meetingDTO);
        boolean result = meetingMapper.updateMet(meetingDTO);
        if (!result) {
            m.addAttribute("msg", "글 작성을 처리하는 동안 오류 발생");
        }
        return "redirect:/meeting/board";
    }

    // session, user_idx,
    @RequestMapping("/apply")
    public ModelAndView insertMetMem(int meeting_idx, @ModelAttribute("user") UserVO user){
        ModelAndView mv = viewMetPost(meeting_idx);
        System.out.println(user);
        if(user.getIdx() == 0){
            mv.addObject("msg", "로그인 후 이용 가능한 서비스입니다. ");
        }else{
            if(meetingMemberMapper.insertMetMem(meeting_idx, user.getIdx())){
                mv.addObject("msg", "신청이 완료되었습니다.");
            }else {
                mv.addObject("msg", false);
            }
        }
        mv.setViewName("MetPostView");
        return mv;
    }

    @RequestMapping("/apply/cancel")
    public ModelAndView deleteMetMemByMem(int meeting_idx,@ModelAttribute("user") UserVO user){
        ModelAndView mv = viewMetPost(meeting_idx);
        if(user.getIdx() == 0){
            mv.addObject("msg", "로그인 후 이용 가능한 서비스입니다. ");
        }else{
            if(meetingMemberMapper.deleteMetMem(meeting_idx,user.getIdx())){
                mv.addObject("msg", "취소가 완료되었습니다.");
            }else {
                mv.addObject("msg", false);
            }
        }
        mv.setViewName("MetPostView");
        return mv;
    }

    @RequestMapping("/accept")
    public ModelAndView updateMetMem(int meeting_idx, @ModelAttribute("user") UserVO user){
        ModelAndView mv = viewMetPost(meeting_idx);
        // 권한 필요
        if(meetingMemberMapper.updateApplyMetMem(meeting_idx,user.getIdx())){
            mv.addObject("msg", "수락이 완료되었습니다.");
        }else {
            mv.addObject("msg", false);
        }
        mv.setViewName("MetPostView");
        return mv;
    }
    @RequestMapping("/accept/cancel")
    public ModelAndView deleteMetMemByLeader(int meeting_idx, int user_idx){
        ModelAndView mv = viewMetPost(meeting_idx);
        // 권한 필요
        if(meetingMemberMapper.updateApplyMetMem(meeting_idx,user_idx)){
            mv.addObject("msg", "취소가 완료되었습니다.");
        }else {
            mv.addObject("msg", false);
        }
        mv.setViewName("MetPostView");
        return mv;

    }
    @RequestMapping("/scrap")
    public ModelAndView insertScrap(int meeting_idx, @ModelAttribute("user") UserVO user){
        ModelAndView mv = viewMetPost(meeting_idx);
        if(user.getIdx() == 0){
            mv.addObject("msg", "로그인 후 이용 가능한 서비스입니다. ");
        }else {
            if (scrapMapper.insertScrap(meeting_idx, user.getIdx())) {
                mv.addObject("msg", "스크랩이 완료되었습니다.");
            } else {
                mv.addObject("msg", false);
            }
        }
        mv.setViewName("MetPostView");
        return mv;

    }
    @RequestMapping("/scrap/cancel")
    public ModelAndView deleteScrap(int meeting_idx, @ModelAttribute("user") UserVO user){

        ModelAndView mv = viewMetPost(meeting_idx);
        if(user.getIdx() == 0){
            mv.addObject("msg", "로그인 후 이용 가능한 서비스입니다. ");
        }else {
            if (meetingMemberMapper.updateApplyMetMem(meeting_idx, user.getIdx())) {
                mv.addObject("msg", "취소가 완료되었습니다.");
            } else {
                mv.addObject("msg", false);
            }
        }
        mv.setViewName("MetPostView");
        return mv;

    }
}
