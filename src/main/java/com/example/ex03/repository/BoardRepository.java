package com.example.ex03.repository;

import com.example.ex03.entity.Board;
import com.example.ex03.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {


    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWriter(@Param("bno") Long bno);


    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno = :bno ")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);


    @Query(value = "SELECT b, w, count(r) FROM Board b "+
            "LEFT JOIN b.writer w "+
            "LEFT JOIN Reply r ON b = r.board " +
            "GROUP BY b",
            countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query(value = "select b, w, count(r) from Board b "+
            "left join b.writer w "+
            "left join Reply r on r.board = b "+
            "where b.bno = :bno ")
    Object getBoardByBno(@Param("bno") Long bno);

}
