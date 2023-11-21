package com.example.unimeeting;

import com.example.unimeeting.domain.Board;
import com.example.unimeeting.repository.BoardRepository;
import com.example.unimeeting.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository BoardRepository;
    @Autowired
    UserRepository userrepository;
    @BeforeEach()
    void pr() {
        System.out.println("=".repeat(80));
    }
    @Test
    @Order(1)
    public void selectList(){
        List<Board> board =BoardRepository.selectList("free","취미");
        board.stream().forEach(System.out::println);
    }
    ////
    @Test
    @Order(2)
    public void selectBoard(){
        Optional<Board> board = Optional.ofNullable(BoardRepository.selectBoard(37));
        System.out.println(BoardRepository.selectBoard(37));
    }
    @Test
    @Order(3)
    @Transactional
    @Rollback(value = true)
    public void save() {
        Board board = Board.builder()
                .title("test999")
                .type("free")
                .content("testcontent999999")
                .build();

        //
        board.setCreatedDatetime(LocalDateTime.now());
        board.setUser(userrepository.findByUserId("hapal").get());
        BoardRepository.save(board);
        System.out.println(board.getIdx());
    }

    @Test
    @Order(4)
    public void findById(){
        System.out.println(BoardRepository.findById(58));
    }

    @Test
    @Order(5)
    public void deleteBoard(){
        System.out.println(BoardRepository.findById(58));
        BoardRepository.deleteById(58);
        System.out.println(BoardRepository.findById(58));
    }
    @Test
    @Order(6)
    @Transactional
    @Rollback(value = true)
    public void updateBoard(){
        Board oldcontext = BoardRepository.findById(58).get();
        System.out.println(BoardRepository.findById(58));
        oldcontext.setContent("test");
        System.out.println(BoardRepository.findById(58));
    }

    @Test
    @Order(7)
    @Transactional
    @Rollback(value = true)
    public void isWriter(){
        Board board = BoardRepository.findById(58).get();
        int result = BoardRepository.isWriter(58,"hapal");
        System.out.println(result);
        //1나와야 함
    }
}