package com.example.unimeeting;

import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.domain.Member;
import com.example.unimeeting.domain.User;
import com.example.unimeeting.repository.MemberRepository;
import com.example.unimeeting.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberRepositoryTest {
    @Autowired
    MemberRepository repository;
@Autowired
    UserRepository user_repository;
    @Test
    @Order(1)
    public void existsByMeetingIdxAndUserIdx(){
        System.out.println(repository.existsByMeetingIdxAndUserIdx(74, 52));
    }

    @Test
    @Order(2)
    public void countByMeetingIdx(){
        System.out.println(repository.countByMeetingIdx(63));
    }

    @Test
    @Order(3)
    @Transactional
    @Rollback(value = false)
    public void deleteByMeetingIdxAndUserIdx(){
        System.out.println(repository.existsByMeetingIdxAndUserIdx(56, 53));
        repository.deleteByMeetingIdxAndUserIdx(56, 53);
        System.out.println(repository.existsByMeetingIdxAndUserIdx(56, 53));
    }

    @Test
    @Order(4)
    @Transactional
    @Rollback(value = false)
    public void save(){

        User user = User.builder()
                .userId("aelim")
                .password("1234")
                .email("devaelim@gmail.com")
                .nickname("aa")
                .phoneNumber("01092708011")
                .category("코딩")
                .build();
//        user.setIdx(52);

//        System.out.println(user);
//        System.out.println(save);

        Member member = new Member();
        member.setUser(user_repository.findByUserId("aelim").get());
        member.setMeetingIdx(87);
        repository.save(member);
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback(value = false)
    public void acceptApply(){
        Member member = repository.findById(95).get();

        member.setAccepted(false);

        System.out.println(repository.findById(95));

    }
}
