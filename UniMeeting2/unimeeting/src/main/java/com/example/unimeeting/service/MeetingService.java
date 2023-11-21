package com.example.unimeeting.service;


import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.domain.Member;
import com.example.unimeeting.domain.User;
import com.example.unimeeting.dto.*;
import com.example.unimeeting.repository.MeetingImageRepository;
import com.example.unimeeting.repository.MeetingRepository;
import com.example.unimeeting.repository.MemberRepository;
import com.example.unimeeting.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingImageRepository  meetingImageRepository;
    private final MemberRepository memberRepository;
    private final ScrapRepository scrapRepository;

    public Meeting findById(Integer id){
        return meetingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public MeetingResponse getMeetingOne(Integer id, User user){
        MeetingResponse ms = new MeetingResponse(meetingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id)),
                memberRepository.countByMeetingIdx(id),
                meetingImageRepository.findImageUrlByMeetingIdx(id),
                meetingRepository.existsByIdxAndUserNickname(id, user.getNickname()),
                memberRepository.existsByMeetingIdxAndUserIdx(id, user.getIdx()));
        return ms;
    }

    public List<MeetingWithDetailsDTO> getAllMeeting(String search){
        List<MeetingWithDetailsDTO> list = new ArrayList<>();
        meetingRepository.findAllByTitleContainingOrContentContaining(search,search)
                .forEach(element -> list.add(new MeetingWithDetailsDTO(element,
                        memberRepository.countByMeetingIdx(element.getIdx()),
                        meetingImageRepository.findImageUrlByMeetingIdx(element.getIdx()).isEmpty() ?
                                "" :meetingImageRepository.findImageUrlByMeetingIdx(element.getIdx()).get(0))
                ));

//        List<MeetingWithDetailsDTO> list = (List<MeetingWithDetailsDTO>) meetingrepository.findAllByTitleContainingOrContentContaining(search,search)
//                .stream().map(e ->
//                        new MeetingWithDetailsDTO(e,
//                                memberRepository.countByMeetingIdx(e.getIdx()),
//                                meetingImageRepository.findImageUrlByMeetingIdx(e.getIdx()).get(0)));

        return list;
    }

    public List<MeetingWithDetailsDTO> getMeetingByCtgr(String category, String search){
        List<MeetingWithDetailsDTO> list = new ArrayList<>();
        meetingRepository.searchMeetingInCategory(category, search,search)
                .forEach(element -> list.add(new MeetingWithDetailsDTO(element,
                        memberRepository.countByMeetingIdx(element.getIdx()),
                        meetingImageRepository.findImageUrlByMeetingIdx(element.getIdx()).isEmpty() ?
                                "" :meetingImageRepository.findImageUrlByMeetingIdx(element.getIdx()).get(0))
                ));
        return list;
    }

    public Meeting addMeeting(AddMeetingRequest meeting, User user){
        return meetingRepository.save(meeting.toEntity(user));
    }

    public Meeting updateMeeting(Integer id, UpdateMeetingRequest update){
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        meeting.update(update.getTitle(),
                       update.getContent(), update.getCategory(),
                       update.getRecruits(), update.getLocation(), update.getStartDatetime());

        return meeting;
    }

    public boolean deleteMeeting(Integer id){
        boolean isSuccess = false;
        if (meetingRepository.findById(id).isPresent()) {
            meetingRepository.deleteById(id);
            isSuccess = true;
        }
        return isSuccess;
    }

    public Member addMember(AddMemberRequest addMemberRequest){
        return memberRepository.save(addMemberRequest.toEntity());
    }

    public Member updateMember(Integer id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        member.update();
        return member;
    }

    public boolean deleteMember(Integer id){
        boolean isSuccess = false;
        if (memberRepository.findById(id).isPresent()) {
            memberRepository.deleteById(id);
            isSuccess = true;
        }
        return isSuccess;
    }
}
