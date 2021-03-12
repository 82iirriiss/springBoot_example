package com.example.ex03.service;

import com.example.ex03.dto.BoardDTO;
import com.example.ex03.dto.ReplyDTO;
import com.example.ex03.entity.Board;
import com.example.ex03.entity.Reply;
import com.example.ex03.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
