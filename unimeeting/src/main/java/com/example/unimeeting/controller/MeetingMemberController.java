package com.example.unimeeting.controller;

import com.example.unimeeting.dao.MeetingMemberMapper;
import com.example.unimeeting.domain.MeetingMemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MeetingMemberController {

    @Autowired
    MeetingMemberMapper mmm;

    @RequestMapping("/member/apply")
    public boolean insertMetMem(MeetingMemberDTO meetingMemberDTO){
        return mmm.insertMetMem(meetingMemberDTO);
    }


}
