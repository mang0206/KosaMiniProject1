package com.example.unimeeting.repository;

import com.example.unimeeting.domain.Board;
import com.example.unimeeting.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT b FROM Board b " +
            "WHERE b.type = :type " +
            "AND (:search IS NULL OR b.title LIKE %:search%) " +
            "ORDER BY b.idx DESC")
    List<Board> selectList(@Param("type") String type, @Param("search") String search);



    @Query("SELECT b FROM Board b WHERE b.idx = :idx")
    Board selectBoard(@Param("idx") Integer idx);

//    @Modifying
//    @Query("DELETE FROM Board WHERE idx = :idx")
//    void deleteBoard(@Param("idx") Integer idx);
    //그냥 deleteByID 써도될듯

//    @Modifying
//    @Query("UPDATE Board SET title = :#{#board.title}, content = :#{#board.content} WHERE idx = :#{#board.idx}")
//    void updateBoard(@Param("board") Board board);

    @Query("SELECT COUNT(b) FROM Board b WHERE b.idx = :idx AND b.user.nickname = :writerNickname")
    int isWriter(@Param("idx") int idx, @Param("writerNickname") String writerNickname);
}
