package com.example.ex03.repository;

import com.example.ex03.entity.Board;
import com.example.ex03.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply(){

        IntStream.rangeClosed(1, 300).forEach(i -> {
            long bno = (long)(Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                                .text("Reply...."+i)
                                .board(board)
                                .replyer("guest")
                                .build();

            replyRepository.save(reply);
        });
    }


    @Test
    public void testGetReplyByBoardOrderbyRno(){
        Board board = Board.builder().bno(100L).build();
        List<Reply> result = replyRepository.getReplyByBoardOrderByRno(board);
        result.forEach(reply -> System.out.println(reply));
    }

}
