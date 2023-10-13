package com.example.unimeeting.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CloudtypeUserVO {
  private int idx;
  private String user_id;
  private String password;
  private String nickname;
  private String email;
  private String cathgory;
  private String phone_number;
}
