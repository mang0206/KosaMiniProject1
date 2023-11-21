package com.example.unimeeting.controller;

import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.dto.MeetingResponse;
import com.example.unimeeting.service.MeetingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.unimeeting.domain.Member;
import com.example.unimeeting.domain.User;
import com.example.unimeeting.dto.*;
import com.example.unimeeting.service.MeetingService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    // Security 구현 전 테스트용 user 객체
    User user = new User(52, "aelim", "1234", "aa", "devaelim@gmail.com", "코딩", "01092708011", "USER");

    // 미팅 글 리스트 조회
    @GetMapping
    public ResponseEntity<List<MeetingWithDetailsDTO>> getMeetings(@RequestParam(required = false) String category,
                                                                   @RequestParam(defaultValue = "") String search){
        List<MeetingWithDetailsDTO> response;

        // category 는 필수 요청이 아님, null 이라면 모든 글 조회. 전달된 값이 있다면 해당 category 글 조회
        // search 는 default "". 아무 것도 전달 받지 않으면 SQL 에서 (SELECT ~ LIKE "") 이므로 검색어 없이 조회 가능.
        response = category == null? meetingService.getAllMeeting(search) : meetingService.getMeetingByCtgr(category,search);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // 미팅 글 하나 조회
    @GetMapping("/{idx}")
    public ResponseEntity<MeetingResponse> getOneMeeting(@PathVariable int idx){
        MeetingResponse response = meetingService.getMeetingOne(idx,user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    // 미팅 글 생성
    // JsonFormat, String 타입으로 전달되는 createdDateTime 을  LocalDateTime 타입으로 인식하기 위해 설정
    @PostMapping
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm")
    public ResponseEntity<Meeting> uploadMeeting(@RequestBody AddMeetingRequest request){
        Meeting meeting = meetingService.addMeeting(request, user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(meeting);
    }

    // 수정할 글 불러오기
    @GetMapping("/update/{idx}")
    public ResponseEntity<Meeting> getMeetingForUpdate(@PathVariable int idx){
        Meeting response = meetingService.findById(idx);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // 미팅 글 수정
    @PutMapping("/{idx}")
    @Transactional
    public ResponseEntity<Meeting> updateMeeting(@PathVariable int idx, @RequestBody UpdateMeetingRequest update){
        System.out.println(update);
        Meeting meeting = meetingService.updateMeeting(idx, update);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(meeting);
    }

    // 미팅 글 삭제
    @DeleteMapping("/{idx}")
    public ResponseEntity<CudResponse> deleteMeeting(@PathVariable int idx){
        HttpStatus status;
        CudResponse response = new CudResponse();
        if (meetingService.deleteMeeting(idx)){
            status = HttpStatus.OK;
            response.setSuccess(true);
            response.setMessage("삭제되었습니다.");
        }else{
            status = HttpStatus.BAD_REQUEST;
            response.setSuccess(false);
            response.setMessage("처리 도중 오류 발생");
        }
        return ResponseEntity
                .status(status)
                .body(response);
    }

    @PostMapping("/apply")
    public ResponseEntity<Member> addMember(@RequestBody AddMemberRequest request){
        Member response = meetingService.addMember(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/apply/{idx}")
    @Transactional
    public ResponseEntity<Member> acceptAcceptApply(@PathVariable int idx){
        Member response = meetingService.updateMember(idx);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // User = 신청자 or 글 작성자
    @DeleteMapping("apply/{idx}")
    public ResponseEntity<CudResponse> deleteMember(@PathVariable int idx){
        HttpStatus status;
        CudResponse response = new CudResponse();
        if (meetingService.deleteMember(idx)){
            status = HttpStatus.OK;
            response.setSuccess(true);
            response.setMessage("삭제되었습니다.");
        }else{
            status = HttpStatus.BAD_REQUEST;
            response.setSuccess(false);
            response.setMessage("처리 도중 오류 발생");
        }
        return ResponseEntity
                .status(status)
                .body(response);
    }

}

