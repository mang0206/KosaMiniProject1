package com.example.unimeeting.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class CloudtypeUserDTO {
  private int idx;
  private String user_id;
  private String password;
  private String nickname;
  private String email;
  private String cathgory;
  private String phone_number;
}
