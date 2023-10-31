package com.example.unimeeting.controller;

import com.example.unimeeting.dao.MeetingMapper;
import com.example.unimeeting.domain.MeetingJoinDTO;
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
import java.util.List;

@Controller
@SessionAttributes("user")
@RequestMapping("/meeting")
public class MeetingController {

    // Mapper
    @Autowired
    MeetingMapper meetingMapper;

    // get Session
    @ModelAttribute("user")
    public UserVO sessionLogin(){
        return null;
    }

    // Get Meeting Board
    // [path]ctgr(category) 카테고리 별로 meeting row 가져옴. (ctgr == null -> 모든 meeting row)
    // [param] search query. (required=false)
    @RequestMapping(value = {"" ,"/{ctgr}"})
    public ModelAndView viewMetBoard(@PathVariable(required = false) String ctgr,@RequestParam(required = false) String search, @RequestParam(defaultValue = "1") int page){

        ModelAndView mv = new ModelAndView();
        System.out.println("ctgr = " + ctgr);
        List<MeetingJoinDTO> meetings = meetingMapper.viewMetBoard(ctgr ,search!=null ? search.trim() : search);
        System.out.println(meetings.size());

//        int metCnt = ctgr == null ? meetingMapper.cntMet() : meetingMapper.cntMetOfCategory(ctgr);
//        metCnt /= 4;
//
//        mv.addObject("cnt", new int[metCnt]);

        // < ========== 카테고리 ========== >
        mv.addObject("ctgr_list", meetingMapper.viewCtgy());
        // ctgr가 null일 때 uri가 '/meeting/' 으로 되어 404 error 발생.
        if(ctgr != null)
            mv.addObject("path_ctgr", "/"+ctgr);

        // 검색어가 있을 때 공백이 있으면 제거
        if(search!=null) search = search.trim();
        // <미팅 글 리스트>
        mv.addObject("met_list", meetings);

        //< ========== 페이지네이션 ========== >
        // pagination, meeting 글을 4개씩 보여줌
        // -> 4개 이하일 경우, page는 하나
        int metCnt = meetingMapper.cntMet(ctgr ,search);
        metCnt = metCnt <  4 ?  1 : (metCnt /= 4);
        // thymeleaf에 each를 쓰기 위해 페이지 수를 배열로 전달
        mv.addObject("met_cnt", new int[metCnt]);

        mv.setViewName("MetBoardView");
        return mv;
    }

    // 글 수정 시 기존 글 내용을 가져오기
    // static/javascript/updateMet.js에서 Ajax를 사용해 값을 불러온다.
    @RequestMapping(value = "/getMetJson", produces = "application/json; charset=utf-8")
    @ResponseBody
    public MeetingDTO getMetJson(int meeting_idx){
        MeetingDTO meetingDTO = meetingMapper.viewMetPost(meeting_idx);
        meetingDTO.setContent_img(meetingMapper.selectMetImg(meeting_idx));
        return meetingDTO;
    }

    // check user before insert met
    @RequestMapping("/goInsertMet")
    public String goInsertMet(@ModelAttribute("user") UserVO user, RedirectAttributes rttr){
        rttr.addFlashAttribute("msg", "로그인 후 이용 가능한 서비스입니다. ");
        return user == null ? "redirect:/meeting": "redirect:/insertMetForm.html";
    }


    // Insert Meeting Post
    @PostMapping("/insertMet")
    public String insertMet(MeetingDTO meetingDTO, Model m, MultipartRequest mreq, @ModelAttribute("user") UserVO user){

        // < ========== 미팅 글 등록 ========== >
        // Session으로 user 정보 등록
        meetingDTO.setWriter_nickname(user.getNickname());
        boolean result = meetingMapper.insertMet(meetingDTO);

        // < ========== 미팅의 이미지 등록 ========== >
        int meeting_idx = meetingMapper.getIdxOfCurrentMet();
        List<MultipartFile> list = mreq.getFiles("images");
        if(!list.isEmpty()){

            String path = "/images/" + meeting_idx;
            // 상대 경로를 찾는 함수인 getRealPath()는 프로젝트 폴더 구조에서 resources가 아닌 webapp 폴더를 우선으로 찾고
            //  해당 폴더가 존재하지 않으면 위와 같이 임시 폴더를 찾아간다.
            // webapp 폴더를 만드는 방법도 있으나, Spring Boot는 jar로 배포되기 때문에 webapp 폴더를 만든다면 정상 배포 되지 않는다.
            String realPath = "C:/workspace/uniMetting/unimeeting/src/main/resources/static" + path;
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
                        meetingMapper.insertMetImg(meetingImageDTO);
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

        // < ========== 미팅 글 정보 가져옴 ========== >
        MeetingDTO meeting = meetingMapper.viewMetPost(meeting_idx);
        // 글의 사진 가져오기
        String[] image_url = meetingMapper.selectMetImg(meeting_idx);
        // 미팅 신청한 인원
        int meeting_member = meetingMapper.countMetMem(meeting_idx);

        ModelAndView mv = new ModelAndView();
        if(meeting != null){
            mv.addObject("meeting", meeting);
            mv.addObject("meeting_member", meeting_member);
            if(image_url.length != 0) mv.addObject("meeting_image", image_url);
            if(meeting_member != 0){
                mv.addObject("applicants", meetingMapper.selectMetMem(meeting_idx));
            }
        }

        // < ========== 신청/스크랩 or 삭제/수정 ========== >
        if(user!=null){
            // 로그인이 되어있을 경우
            // apply, scrap -> true(신청/스크랩 버튼), false(삭제/수정 버튼)
            mv.addObject("apply", meetingMapper.checkMetMem(meeting_idx, user.getIdx()) == 0);
            mv.addObject("scrap", meetingMapper.checkScrap(meeting_idx, user.getIdx()) == 0);
            System.out.println(meetingMapper.isWriter(meeting_idx, user.getNickname())+"-------------");
            mv.addObject("isWriter", meetingMapper.isWriter(meeting_idx, user.getNickname()) == 1);
        }else{
            // 로그인이 되어있지 않을 경우 (/apply, /scrap 에서 처리)
            mv.addObject("apply", true);
            mv.addObject("scrap", true);
        }

        mv.setViewName("MetPostView");
        return mv;
    }

//    @RequestMapping(value = "/viewMeetingApplicant", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public List<MeetingApplicantVO> viewMeetingApplicant(int meeting_idx){
//        return meetingMapper.selectMetMem(meeting_idx);
//    }


    // delete meeting
    @RequestMapping("/deleteMet")
    public String deleteMetPost(int idx){
        return meetingMapper.deleteMeeting(idx) ? "redirect:/meeting" : "redirect:/";
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
            if(meetingMapper.checkMetMem(meeting_idx, user.getIdx()) > 0){
                rttr.addFlashAttribute("msg", "이미 신청한 소모임입니다.");
            }else {
                meetingMapper.insertMetMem(meeting_idx, user.getIdx());
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
            if(meetingMapper.deleteMetMem(meeting_idx,user.getIdx())){
                rttr.addFlashAttribute("msg", "취소가 완료되었습니다.");
            }else {
                rttr.addFlashAttribute("msg", "ERROR!");
            }
        }
        return "redirect:/meeting/post?meeting_idx=" + meeting_idx;
    }

    //    마이페이지와 연동 예정
    @RequestMapping("/accept")
    public String accept(int meeting_idx, int user_idx, RedirectAttributes rttr){
        // 권한 필요
        if(meetingMapper.acceptApply(meeting_idx,user_idx)){
            rttr.addFlashAttribute("msg", "수락이 완료되었습니다.");
        }else {
            rttr.addFlashAttribute("msg", false);
        }
        return "redirect:/meeting/post?meeting_idx=" + meeting_idx;
    }
    @RequestMapping("/accept/cancel")
    public String cancelAccept(int meeting_idx, int user_idx, RedirectAttributes rttr){
        // 권한 필요
        if(meetingMapper.acceptCancel(meeting_idx,user_idx)){
            rttr.addFlashAttribute("msg", "수락이 취소 되었습니다.");
        }else {
            rttr.addFlashAttribute("msg", false);
        }
        return "redirect:/meeting/post?meeting_idx=" + meeting_idx;
    }

    @RequestMapping("/accept/delete")
    public String deleteAccept(int meeting_idx, int user_idx, RedirectAttributes rttr){
        // 권한 필요
        if(meetingMapper.deleteMetMem(meeting_idx,user_idx)){
            rttr.addFlashAttribute("msg", "수락을 거절했습니다. ");
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
            if (meetingMapper.insertScrap(meeting_idx, user.getIdx())) {
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
            if (meetingMapper.deleteScrap(meeting_idx, user.getIdx())) {
                rttr.addFlashAttribute("msg", "취소가 완료되었습니다.");
            } else {
                rttr.addFlashAttribute("msg", false);
            }
        }
        return "redirect:/meeting/post?meeting_idx=" + meeting_idx;

    }

//    @RequestMapping(value = "/viewMeetingApplicant", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public List<MeetingApplicantVO> viewMeetingApplicant(int meeting_idx){
//        return meetingMapper.selectMetMem(meeting_idx);
//    }
}