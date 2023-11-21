package com.example.unimeeting.service;

import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.domain.Member;
import com.example.unimeeting.domain.User;
import com.example.unimeeting.dto.MeetingWithDetailsDTO;
import com.example.unimeeting.repository.MeetingImageRepository;
import com.example.unimeeting.repository.MeetingRepository;
import com.example.unimeeting.repository.MemberRepository;
import com.example.unimeeting.repository.ScrapRepository;

import com.example.unimeeting.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MypageService {

  private final MeetingRepository meetingR;
  private final MeetingImageRepository imageR;
  private final MemberRepository memberR;
  private final UserRepository userR;
//  참여한 소모임 리스트
  public List<MeetingWithDetailsDTO> joinMeeting(int idx) {
      List<Meeting> list = meetingR.searchMeetingInMemberIDX(idx);
      List<MeetingWithDetailsDTO> listDTO = new ArrayList<>();
      MeetingWithDetailsDTO dto;

      for(int i=0; i<list.size(); i++){
          Meeting m = list.get(i);
          List<String> imgUrls = imageR.findImageUrlByMeetingIdx(m.getIdx());
          String imgUrl = imgUrls.isEmpty() ? "":imgUrls.get(0);
          dto = new MeetingWithDetailsDTO(m, memberR.countByMeetingIdx(m.getIdx()) ,imgUrl);
          listDTO.add(dto);
      }
    return listDTO;
  }

//  생성한 소모임 리스트
    public List<MeetingWithDetailsDTO> createMeeting(String nickname) {
        List<Meeting> list = meetingR.findByUserNickname(nickname);
        List<MeetingWithDetailsDTO> listDTO = new ArrayList<>();
        MeetingWithDetailsDTO dto;

        for(int i=0; i<list.size(); i++){
            Meeting m = list.get(i);
            List<String> imgUrls = imageR.findImageUrlByMeetingIdx(m.getIdx());
            String imgUrl = imgUrls.isEmpty() ? "":imgUrls.get(0);
            dto = new MeetingWithDetailsDTO(m, memberR.countByMeetingIdx(m.getIdx()) ,imgUrl);
            listDTO.add(dto);
        }
        return listDTO;
    }

//    스크랩한 소모임 리스트
    public List<MeetingWithDetailsDTO> scrapMeeting(int idx) {
        List<Meeting> list = meetingR.searchMeetingInScrapIDX(idx);
        List<MeetingWithDetailsDTO> listDTO = new ArrayList<>();
        MeetingWithDetailsDTO dto;

        for(int i=0; i<list.size(); i++){
            Meeting m = list.get(i);
            List<String> imgUrls = imageR.findImageUrlByMeetingIdx(m.getIdx());
            String imgUrl = imgUrls.isEmpty() ? "":imgUrls.get(0);
            dto = new MeetingWithDetailsDTO(m, memberR.countByMeetingIdx(m.getIdx()) ,imgUrl);
            listDTO.add(dto);
        }
        return listDTO;
    }

    public User findUser(int idx){
      return userR.findById(idx).get();
    }

//    정보 수정을 위한 Update
    @Transactional
    public boolean updateUser(User user, int idx) {
        boolean result = true;
        try {
            User ouser = userR.findById(idx).get();
            ouser.setNickname(user.getNickname());
            ouser.setUserId(user.getUserId());
        } catch(Exception e) {
            System.out.println("수정 실패");
            result = false;
        }
        return result;
    }

//    회원 탈퇴를 위한 delete
    @Transactional
    public boolean deleteUser(int idx) {
        boolean result = true;
        try {
            userR.deleteById(idx);
        } catch(Exception e) {
            System.out.println("삭제 실패");
            result = false;
        }
        return result;
    }
}
