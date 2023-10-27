package com.example.unimeeting.controller;
import com.example.unimeeting.dao.NoticeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.example.unimeeting.domain.NoticeVO;
import com.example.unimeeting.domain.UserVO;
@Controller
@RequestMapping("/board")
@SessionAttributes("user")
public class NoticeController {
    @ModelAttribute("user")
    public UserVO sessionLogin() {
        return null;
    }
    private static final Logger logger =
            LoggerFactory.getLogger(
                    NoticeController.class);
    @Autowired
    NoticeMapper noticeMapper;
    //=======공지 게시판 글 목록=========//
    @RequestMapping("/{type}")
    public ModelAndView process(@PathVariable("type") String type, @RequestParam(required = false) String search,@ModelAttribute("user") UserVO user) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("noticeList");
        mav.addObject("list", noticeMapper.selectList(type, search != null ? search.trim() : null));
        mav.addObject("writehide",user);
        return mav;
    }
    // ======공지 글 상세페이지 ======//
    @RequestMapping("/detail")
    public ModelAndView viewDetail(int idx) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("noticeDetail");
        mav.addObject("detail", noticeMapper.selectNotice(idx));
        return mav;
    }
    //========공지 작성=================//
    @RequestMapping("/write")
    public String write(NoticeVO noticeVO, @ModelAttribute("user") UserVO user) {
        System.out.println(noticeVO + "------------------------------");
        noticeVO.setWriter_nickname(user.getNickname());
        noticeMapper.insertNotice(noticeVO);
        return "redirect:/board/" + noticeVO.getType();
    }
    //=====공지 삭제 ===========//
    @RequestMapping("/delete")
    public String deleteNotice(int idx, String type, @ModelAttribute("user") UserVO user) {
        if (noticeMapper.isWriter(idx, user.getNickname()) == 1) {
            noticeMapper.deleteNotice(idx);
        }
        return "redirect:/board/" + type;
    }
    @RequestMapping("/update")
    public String updateNotice(NoticeVO noticeVO, @ModelAttribute("user") UserVO user) {
        if (noticeMapper.isWriter(noticeVO.getIdx(), user.getNickname()) == 1) {
            noticeMapper.updateNotice(noticeVO);
        }
        return "redirect:/board/detail?idx=" + noticeVO.getIdx();
    }
    @RequestMapping(value = "/updateJSON", produces = "application/json; charset=utf-8")
    @ResponseBody
    public NoticeVO updateJSON(int idx) {
        return noticeMapper.selectNotice(idx);
    }

}
