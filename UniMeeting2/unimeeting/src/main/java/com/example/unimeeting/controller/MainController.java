package com.example.unimeeting.controller;


import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.dto.MeetingWithDetailsDTO;
import com.example.unimeeting.service.MainService;
import com.example.unimeeting.service.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final MainService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 모임 리스트",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeetingWithDetailsDTO.class)) })
    })
    @GetMapping("/meetings/search")
    public ResponseEntity<List<Meeting>> searchList() {
        ResponseEntity<List<Meeting>> entity = new ResponseEntity<>(service.meetingsearch(""), HttpStatus.OK);

        return entity;
    }
//    @GetMapping("/meetings/popular")
//    public ResponseEntity<List<Meeting>> popularSort() {
//        ResponseEntity<List<Meeting>> entity = new ResponseEntity<>(service.meetingpopular(), HttpStatus.OK);
//
//        return entity;
//    }
    @GetMapping("/meetings/title")
    public ResponseEntity<List<Meeting>> titleSort() {
        ResponseEntity<List<Meeting>> entity = new ResponseEntity<>(service.meetingtitle(), HttpStatus.OK);

        return entity;
    }
    @GetMapping("/meetings/datetime")
    public ResponseEntity<List<Meeting>> datetimeSort() {
        ResponseEntity<List<Meeting>> entity = new ResponseEntity<>(service.meetingdatetime(), HttpStatus.OK);

        return entity;
    }
}
