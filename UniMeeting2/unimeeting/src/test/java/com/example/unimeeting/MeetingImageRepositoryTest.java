package com.example.unimeeting;

import com.example.unimeeting.repository.MeetingImageRepository;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class MeetingImageRepositoryTest {
    @Autowired
    MeetingImageRepository repository;

    @Test
    public void findImageUrlByMeetingIdx(){
        List<String> list = repository.findImageUrlByMeetingIdx(56);
        list.forEach(System.out::println);
    }

}
