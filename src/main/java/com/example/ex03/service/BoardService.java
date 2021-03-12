package com.example.ex03.service;

import com.example.ex03.dto.BoardDTO;
import com.example.ex03.dto.PageRequestDTO;
import com.example.ex03.dto.PageResultDTO;
import com.example.ex03.entity.Board;
import com.example.ex03.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO dto);

    default BoardDTO entityToDto(Board board, Member member, Long ReplyCount){

        BoardDTO dto = BoardDTO.builder()
                                .bno(board.getBno())
                                .title(board.getTitle())
                                .content(board.getContent())
                                .writerEmail(member.getEmail())
                                .writerName(member.getName())
                                .replyCount(ReplyCount.intValue())
                                .modDate(board.getModDate())
                                .regDate(board.getRegDate())
                                .build();
        return dto;
    }

    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder()
                                .email(dto.getWriterEmail()) //PK만 지정해 주면 됨.
                                .build();

        Board board = Board.builder()
                            .bno(dto.getBno())
                            .title(dto.getTitle())
                            .content(dto.getContent())
                            .writer(member)
                            .build();

        return board;
    }
}
