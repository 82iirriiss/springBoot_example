package com.example.ex03.service;

import com.example.ex03.dto.GuestbookDTO;
import com.example.ex03.dto.PageRequestDTO;
import com.example.ex03.dto.PageResultDTO;
import com.example.ex03.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    GuestbookService service;

    @Test
    public  void testRegister(){
        GuestbookDTO dto = GuestbookDTO.builder()
                            .title("Sample Title...")
                            .content("Sample Content...")
                            .writer("user0")
                            .build();

        System.out.println(service.register(dto));
    }


    @Test
    public void testGetList(){
        PageRequestDTO requestDto = PageRequestDTO.builder()
                                    .page(1)
                                    .size(10)
                                    .type("tcw")
                                    .keyword("수정")
                                    .build();

        PageResultDTO<GuestbookDTO, Guestbook> result = service.getList(requestDto);

        System.out.println("PREV:" + result.isPrev());
        System.out.println("NEXT:" + result.isNext());
        System.out.println("START:" + result.getStart());
        System.out.println("END:" + result.getEnd());
        System.out.println("===================DTO=========================");
        result.getDtoList().stream().forEach(dto -> {
            System.out.println(dto);
        });
        System.out.println("===================PageList=============================");
        result.getPageList().stream().forEach( page -> System.out.println(page));
    }
}
