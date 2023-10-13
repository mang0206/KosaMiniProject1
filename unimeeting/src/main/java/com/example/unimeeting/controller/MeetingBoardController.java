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

    // ctrg category, path -> default all
    // search searchword, query
    // page,page number query -> default 1
    @RequestMapping(value = {"/meeting/board", "/meeting/board/{ctgr}"})
    public ModelAndView viewMetBoard(@PathVariable(required = false) String ctgr, String search, @RequestParam(defaultValue = "1") int page){
        ModelAndView mv = new ModelAndView();
        List<String> category = metmap.viewCtgy();
        mv.addObject("ctgr_list", category);
        //page
//        int page_num = metmap.countMet(ctgr, search);
//        mv.addObject("page_num", page_num);
        List<MeetingDTO> meetings = metmap.viewMetBoard(ctgr,search, (page-1)*4);
        mv.addObject("met_list", meetings);

        mv.setViewName("testHTML");
        return mv;
    }

//    @RequestMapping("/insertMet")
//    public ModelAndView insertMeeting(MeetingDTO meetingDTO){
//        List<MeetingDTO> list = dao.seeBookKindAverage();
//        ModelAndView mav= new ModelAndView();
//        if (list==null) {
//            mav.addObject("msg", "추출된 결과가 없어요");
//        } else {
//            mav.addObject("list", list);
//        }
//        mav.setViewName("testHTML");
//        return mav;
//    }
}
