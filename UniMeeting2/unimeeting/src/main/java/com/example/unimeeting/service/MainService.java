package com.example.unimeeting.service;

import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.repository.MeetingRepository;
import com.example.unimeeting.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MainService {
    private final MeetingRepository meetingRepository;
    private final ScrapRepository scrapRepository;
    private static List<Meeting> list;
    public List<Meeting> findAll() { return list; }

    public List<Meeting> meetingsearch(String keyword){

        return meetingRepository.searchByList(keyword);
    }

//    public List<Meeting> meetingpopular(){
//
//        return meetingRepository.findAllByOrderByScrab();
//    }

    public List<Meeting> meetingtitle(){

        return meetingRepository.findAllByOrderByTitle();
    }

    public List<Meeting> meetingdatetime(){

        return meetingRepository.findAllByOrderByCreatedDatetimeDesc();
    }

}
