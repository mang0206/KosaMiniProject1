package com.example.unimeeting.dao;

import com.example.unimeeting.domain.UserVO;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    UserVO getUserByUserId(String userId);

    @Insert("INSERT INTO user (user_id, password,phone_number, nickname, email, category) VALUES (#{user.user_id}, #{user.password},#{user.phone_numbebr}, #{user.nickname}, #{user.email}, #{user.category})")
    void insertUser(@Param("user") UserVO user);

}
