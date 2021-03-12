package com.example.ex03.repository;

import com.example.ex03.entity.Board;
import com.example.ex03.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("delete from Reply r where r.board.bno = :bno ")
    void deleteByBoard(@Param("bno") Long bno);

    List<Reply> getReplyByBoardOrderByRno(Board board);
}
