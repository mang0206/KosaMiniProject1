package com.example.unimeeting.dao;

import com.example.unimeeting.domain.UserVO;
import org.apache.ibatis.annotations.*;

public interface UserMapper {


    @Select("SELECT user_id, password FROM user WHERE user_id = #{user_Id}")
    UserVO signIn(String user_Id);

    @Select("SELECT * FROM user WHERE user_id = #{user_Id}")
    UserVO idcheck(String user_Id);

    @Select("SELECT * FROM user WHERE nickname = #{nickname}")
    UserVO nicknamecheck(String nickname);

    @Select("SELECT * FROM user WHERE email = #{email}")
    UserVO emailcheck(String email);


    @Insert("INSERT INTO user (user_id, password,phone_number, nickname, email, category) VALUES (#{user.user_id}, #{user.password},#{user.phone_number}, #{user.nickname}, #{user.email}, #{user.category})")
    void insertUser(@Param("user") UserVO user);

}
