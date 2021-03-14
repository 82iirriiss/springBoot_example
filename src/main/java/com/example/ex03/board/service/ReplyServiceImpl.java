package com.example.ex03.board.service;

import com.example.ex03.board.dto.ReplyDTO;
import com.example.ex03.board.entity.Board;
import com.example.ex03.board.entity.Reply;
import com.example.ex03.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    @Override
    public List<ReplyDTO> getList(Long bno) {

        List<Reply> result = replyRepository.getReplyByBoardOrderByRno(Board.builder().bno(bno).build());
        return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReplyDTO dto) {

        Reply reply = dtoToEntity(dto);
        replyRepository.save(reply);
        return reply.getRno();
    }

    @Override
    public void modify(ReplyDTO dto) {

        replyRepository.save(dtoToEntity(dto));
    }

    @Override
    public void remove(Long rno) {

        replyRepository.deleteById(rno);
    }

}
