package com.example.ex03.repository;

import com.example.ex03.board.entity.Board;
import com.example.ex03.board.entity.Member;
import com.example.ex03.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user"+i+"@aaa.com").build();

            Board board = Board.builder()
                                .title("Title...."+i)
                                .content("Content...."+i)
                                .writer(member)
                                .build();

            boardRepository.save(board);
        });
    }


    @Transactional
    @Test
    public void testRead1(){

        Optional<Board> result = boardRepository.findById(100L);
        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());
    }


    @Test
    public void testReadWithWriter(){

        Object result = boardRepository.getBoardWriter(100L);
        Object[] arr = (Object[]) result;

        System.out.println("---------------------------------");
        System.out.println(Arrays.toString(arr));
    }


    @Test
    public void testReadWithReply(){
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }


    @Test
    public void testGetBoardWithReplyCount(){
        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(boards -> {
            Object[] arr = boards;
            System.out.println(Arrays.toString(arr));
        });
    }


    @Test
    public void testGetBoardByBno(){

        Object result = boardRepository.getBoardByBno(100L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }


    @Test
    public void testSearch1(){
        boardRepository.search1();
    }


    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending().and(Sort.by("title").ascending()));
        Page<Object[]> result = boardRepository.searchPage("tcw", "1", pageable);
    }
}
