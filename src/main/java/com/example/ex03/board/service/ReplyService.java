package com.example.ex03.board.service;

import com.example.ex03.board.dto.ReplyDTO;
import com.example.ex03.board.entity.Board;
import com.example.ex03.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    List<ReplyDTO> getList(Long bno);

    Long  register(ReplyDTO dto);

    void modify(ReplyDTO dto);

    void remove(Long rno);

    default ReplyDTO entityToDto(Reply entity){
        ReplyDTO replyDTO = ReplyDTO.builder()
                                    .rno(entity.getRno())
                                    .text(entity.getText())
                                    .replyer(entity.getReplyer())
                                    .bno(entity.getBoard().getBno())
                                    .regDate(entity.getRegDate())
                                    .modDate(entity.getModDate())
                                    .build();
        return replyDTO;
    }

    default Reply dtoToEntity(ReplyDTO dto){
        Board board = Board.builder().bno(dto.getBno()).build();
        Reply entity = Reply.builder()
                            .rno(dto.getRno())
                            .text(dto.getText())
                            .replyer(dto.getReplyer())
                            .board(board)
                            .build();
        return entity;
    }
}
