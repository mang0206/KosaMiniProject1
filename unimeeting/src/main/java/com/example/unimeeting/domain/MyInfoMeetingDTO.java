package com.example.unimeeting.domain;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyInfoMeetingDTO {
  private String division;
  private List<MeetingCntDTO> list;
}
