package com.example.unimeeting.domain;
import com.example.unimeeting.domain.UserVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    UserVO getUserByUserId(String userId);

    @Insert("INSERT INTO user (user_id, password, nickname, email, category) VALUES (#{user.user_id}, #{user.password}, #{user.nickname}, #{user.email}, #{user.category})")
    void insertUser(@Param("user") UserVO user);

    /* @Select("SELECT COUNT(*) FROM user WHERE user_id = #{userId}")
    int checkDuplicateId(String userId);

    @Select("SELECT u.idx, u.user_id, u.password, u.nickname, u.email " +
            "FROM users u LEFT OUTER JOIN meeting m ON u.idx=m.idx " +
            "WHERE u.user_id=#{userId}")
    UserVO selectCheckMember(String userId);

    @Select("SELECT * FROM users u JOIN meetingg m ON u.idx=m.idx WHERE m.idx=#{idx}")
    UserVO selectMember(Integer idx);*/

}
