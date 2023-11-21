package com.example.unimeeting;

import com.example.unimeeting.domain.Meeting;
import com.example.unimeeting.domain.Scrap;
import com.example.unimeeting.domain.User;
import com.example.unimeeting.repository.ScrapRepository;
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
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScrapRepositoryTest {

    @Autowired
    ScrapRepository repository;

    @Autowired
    UserRepository user_repository;

    @Test
    @Order(1)
    public void existsByMeetingIdxAndUserIdx() {
        System.out.println(repository.existsByMeetingIdxAndUserIdx(55,55));
    }

    @Test
    @Order(2)
    @Transactional
//    @Rollback(value = false)
    public void deleteByMeetingIdxAndUserIdx() {
       repository.deleteByMeetingIdxAndUserIdx(55,55);
    }

    @Test
    @Order(3)
    @Transactional
//    @Rollback(value = false)
    public void save() {

        Scrap scrap = new Scrap();
        scrap.setUser(user_repository.findByUserId("aelim").get());
        scrap.setMeetingIdx(87);
        repository.save(scrap);

    }

    @Test
    @Order(4) //dh
    public void findAll() {
        List<Scrap> list = repository.findAll();
        list.forEach(System.out::println);
    }
}
