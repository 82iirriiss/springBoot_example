package com.example.ex03.service;

import com.example.ex03.board.dto.ReplyDTO;
import com.example.ex03.board.service.ReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    private ReplyService service;


    @Test
    public void testGetList(){

        List<ReplyDTO> result = service.getList(100L);

        result.stream().forEach(dto -> System.out.println(dto));
    }
}
