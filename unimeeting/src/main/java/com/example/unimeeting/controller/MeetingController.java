package com.example.unimeeting.controller;

import com.example.unimeeting.dao.MeetingImageMapper;
import com.example.unimeeting.dao.MeetingMapper;
import com.example.unimeeting.dao.MeetingMemberMapper;
import com.example.unimeeting.dao.ScrapMapper;
import com.example.unimeeting.domain.MeetingCntDTO;
import com.example.unimeeting.domain.MeetingDTO;
import com.example.unimeeting.domain.MeetingImageDTO;
import com.example.unimeeting.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes("user")
@RequestMapping("/meeting")
public class MeetingController {

    // Mapper
    @Autowired
    MeetingMapper meetingMapper;
    @Autowired
    MeetingMemberMapper meetingMemberMapper;
    @Autowired
    ScrapMapper scrapMapper;
    @Autowired
    MeetingImageMapper meetingImageMapper;

    // get Session
    @ModelAttribute("user")
    public UserVO sessionLogin(){
        return null;
    }

    // Get Category
    @RequestMapping(value = "/getCategory", produces = "application/json; charset=utf-8" )
    @ResponseBody
    public List<String> getCategory(){
        return meetingMapper.viewCtgy();
    }

    // Get Meeting Board
    // [path]ctgr(category) 카테고리 별로 meeting row 가져옴. (ctgr == null -> 모든 meeting row)
    // [param] search query. (required=false)
    @RequestMapping(value = {"" ,"/{ctgr}"})
        public ModelAndView viewMetBoard(@PathVariable(required = false) String ctgr,@RequestParam(required = false) String search, @RequestParam(defaultValue = "1") int page){
        ModelAndView mv = new ModelAndView();
        mv.addObject("ctgr_list", getCategory());
//        List<MeetingDTO> meetings = meetingMapper.viewMetBoard(ctgr ,search!=null ? search.trim() : search, (page-1)*4);
        System.out.println("ctgr = " + ctgr);
        List<MeetingCntDTO> meetings = meetingMapper.viewMetBoard(ctgr ,search!=null ? search.trim() : search);
        mv.addObject("met_list", meetings);
        System.out.println(meetings.size());

        if(ctgr != null)
            mv.addObject("path_ctgr", ctgr);

        mv.setViewName("MetBoardView");
        return mv;
    }

    // Insert Meeting Post
    @PostMapping("/insertMet")
    public String insertMet(MeetingDTO meetingDTO, Model m, MultipartRequest mreq, @ModelAttribute("user") UserVO user){


        meetingDTO.setWriter_nickname(user.getNickname());
        boolean result = meetingMapper.insertMet(meetingDTO);
        int meeting_idx = meetingMapper.getIdxOfCurrentMet();
        List<MultipartFile> list = mreq.getFiles("images");
        if(!list.isEmpty()){

            String path = "/images/" + meeting_idx;
            // 상대 경로를 찾는 함수인 getRealPath()는 프로젝트 폴더 구조에서 resources가 아닌 webapp 폴더를 우선으로 찾고
            //  해당 폴더가 존재하지 않으면 위와 같이 임시 폴더를 찾아간다.
            // webapp 폴더를 만드는 방법도 있으나, Spring Boot는 jar로 배포되기 때문에 webapp 폴더를 만든다면 정상 배포 되지 않는다.
            String realPath = "C:/UNIMEETING/unimeeting/src/main/resources/static" + path;
            File isDir = new File(realPath);
            if (!isDir.isDirectory()) {
                isDir.mkdirs();
            }

            for (MultipartFile mfile : list) {
                // replace -> 파일 이름의 공백을 언더바로 변경
                String fileName = mfile.getOriginalFilename().replace(" ", "_");
                System.out.println(fileName);

                try {
                    File f = new File(realPath + "/"+ fileName);
                    if (f.exists()) {
                        System.out.println("already exist");
                    } else {
                        mfile.transferTo(f);
                        MeetingImageDTO meetingImageDTO = new MeetingImageDTO(meeting_idx, path+"/"+ fileName);
                        meetingImageMapper.insertMetImg(meetingImageDTO);
                        System.out.println("upload images success");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("upload images error");
                }
            }
        }
        if (!result) {
            m.addAttribute("msg", "글 작성을 처리하는 동안 오류 발생");
        }
        return "redirect:/meeting";
    }


    // Get Meeting Post
    @GetMapping("/post")
    public ModelAndView viewMetPost(int meeting_idx, @ModelAttribute("user") UserVO user){
        MeetingDTO meeting = meetingMapper.viewMetPost(meeting_idx);
        // get images of meeting post
        String[] image_url = meetingImageMapper.selectMetImg(meeting_idx);
        // get
        int meeting_member = meetingMapper.countMetMem(meeting_idx);
        ModelAndView mv = new ModelAndView();
        if(meeting != null){
            mv.addObject("meeting", meeting);
            mv.addObject("meeting_member", meeting_member);
            if(image_url.length != 0) mv.addObject("meeting_image", image_url);
        }

        if(user!=null){
            mv.addObject("apply", meetingMemberMapper.checkMetMem(meeting_idx, user.getIdx()) == 0);
            mv.addObject("scrap", scrapMapper.checkScrap(meeting_idx, user.getIdx()) == 0);
            System.out.println(meetingMapper.isWriter(meeting_idx, user.getNickname())+"-------------");
            mv.addObject("isWriter", meetingMapper.isWriter(meeting_idx, user.getNickname()) == 1);
        }else{
            mv.addObject("apply", true);
            mv.addObject("scrap", true);
        }

        System.out.println(mv);
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
        return meetingMapper.deleteMeeting(idx, writer_nickname) ? "redirect:/meeting" : "redirect:/";
    }

    // update meeting
    @RequestMapping("/updateMet")
    public String updateMetPost(MeetingDTO meetingDTO,Model m){
        System.out.println("----------------------updateMeetingDTO------------------");
        System.out.println(meetingDTO);
        boolean result = meetingMapper.updateMet(meetingDTO);
        if (!result) {
            m.addAttribute("msg", "글 작성을 처리하는 동안 오류 발생");
        }
        return "redirect:/meeting";
    }

    // session, user_idx,
    @RequestMapping("/apply")
    public String insertMetMem(@ModelAttribute("user") UserVO user, @RequestParam("meeting_idx") int meeting_idx, RedirectAttributes rttr){
        if(user == null){
            rttr.addFlashAttribute("msg", "로그인 후 이용 가능한 서비스입니다. ");
        }else{
            if(meetingMemberMapper.checkMetMem(meeting_idx, user.getIdx()) > 0){
                rttr.addFlashAttribute("msg", "이미 신청한 소모임입니다.");
            }else {
                meetingMemberMapper.insertMetMem(meeting_idx, user.getIdx());
                rttr.addFlashAttribute("msg", "신청이 완료되었습니다.");
            }
        }
        return "redirect:/meeting/post?meeting_idx=" + meeting_idx;
    }

    @RequestMapping("/apply/cancel")
    public String deleteMetMemByMem(@ModelAttribute("user") UserVO user, int meeting_idx, RedirectAttributes rttr){
        if(user == null){
            rttr.addFlashAttribute("msg", "로그인 후 이용 가능한 서비스입니다. ");
        }else{
            if(meetingMemberMapper.deleteMetMem(meeting_idx,user.getIdx())){
                rttr.addFlashAttribute("msg", "취소가 완료되었습니다.");
            }else {
                rttr.addFlashAttribute("msg", "ERROR!");
            }
        }
        return "redirect:/meeting/post?meeting_idx=" + meeting_idx;
    }

//    마이페이지와 연동 예정
    @RequestMapping("/accept")
    public String updateMetMem(int meeting_idx, int user_idx, RedirectAttributes rttr){
        // 권한 필요
        if(meetingMemberMapper.updateApplyMetMem(meeting_idx,user_idx)){
            rttr.addFlashAttribute("msg", "수락이 완료되었습니다.");
        }else {
            rttr.addFlashAttribute("msg", false);
        }
        return "redirect:/meeting/post?meeting_idx=" + meeting_idx;
    }
    @RequestMapping("/accept/cancel")
    public String deleteMetMemByLeader(int meeting_idx, int user_idx, RedirectAttributes rttr){
        // 권한 필요
        if(meetingMemberMapper.updateApplyMetMem(meeting_idx,user_idx)){
            rttr.addFlashAttribute("msg", "수락이 취소 되었습니다.");
        }else {
            rttr.addFlashAttribute("msg", false);
        }
        return "redirect:/meeting/post?meeting_idx=" + meeting_idx;
    }

    @RequestMapping("/scrap")
    public String insertScrap(int meeting_idx, @ModelAttribute("user") UserVO user, RedirectAttributes rttr){
        if(user ==  null){
            rttr.addFlashAttribute("msg", "로그인 후 이용 가능한 서비스입니다. ");
        }else {
            if (scrapMapper.insertScrap(meeting_idx, user.getIdx())) {
                rttr.addFlashAttribute("msg", "스크랩이 완료되었습니다.");
            } else {
                rttr.addFlashAttribute("msg", false);
            }
        }
        return "redirect:/meeting/post?meeting_idx=" + meeting_idx;
    }
    @RequestMapping("/scrap/cancel")
    public String deleteScrap(int meeting_idx, @ModelAttribute("user") UserVO user, RedirectAttributes rttr){

        if(user == null){
            rttr.addFlashAttribute("msg", "로그인 후 이용 가능한 서비스입니다. ");
        }else {
            if (scrapMapper.deleteScrap(meeting_idx, user.getIdx())) {
                rttr.addFlashAttribute("msg", "취소가 완료되었습니다.");
            } else {
                rttr.addFlashAttribute("msg", false);
            }
        }
        return "redirect:/meeting/post?meeting_idx=" + meeting_idx;

    }
}
