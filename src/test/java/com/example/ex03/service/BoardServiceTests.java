package com.example.ex03.service;

import com.example.ex03.dto.BoardDTO;
import com.example.ex03.dto.PageRequestDTO;
import com.example.ex03.dto.PageResultDTO;
import com.example.ex03.entity.Board;
import com.example.ex03.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService service;

    @Test
    public void testRegister(){

        BoardDTO dto = BoardDTO.builder()
                                .title("Title Register Test...")
                                .content("Content Register Test...")
                                .writerEmail("user55@aaa.com")
                                .build();


        Long bno = service.register(dto);
    }


    @Test
    public void testGetList(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> result =  service.getList(pageRequestDTO);
        List<BoardDTO> list = result.getDtoList();

        list.stream().forEach(dto -> System.out.println(dto));
    }


    @Test
    public void testGet(){

        System.out.println(service.get(2L));
    }


    @Test
    public void testRemove(){

        Long bno = 1L;
        service.removeWithReplies(bno);
    }


    @Test
    public void testModify(){
        BoardDTO dto = BoardDTO.builder()
                                .bno(2L)
                                .title("Modify Title...")
                                .content("Modify Content...").build();

        service.modify(dto);
    }
}
