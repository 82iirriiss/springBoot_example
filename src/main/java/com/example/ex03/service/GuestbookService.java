package com.example.ex03.service;

import com.example.ex03.dto.GuestbookDTO;
import com.example.ex03.dto.PageRequestDTO;
import com.example.ex03.dto.PageResultDTO;
import com.example.ex03.entity.Guestbook;

import java.util.List;

public interface GuestbookService {

    Long register(GuestbookDTO dto);

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    GuestbookDTO read(Long gno);

    void remove(Long gno);

    void modify(GuestbookDTO dto);

    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }


    default GuestbookDTO entityToDto(Guestbook guestbook){
        GuestbookDTO dto = GuestbookDTO.builder()
                        .gno(guestbook.getGno())
                        .title(guestbook.getTitle())
                        .content(guestbook.getContent())
                        .writer(guestbook.getWriter())
                        .regDate(guestbook.getRegDate())
                        .modDate(guestbook.getModDate())
                        .build();

        return dto;
    }
}
