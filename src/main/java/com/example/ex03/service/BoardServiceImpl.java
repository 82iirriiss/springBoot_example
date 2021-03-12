package com.example.ex03.service;

import com.example.ex03.dto.BoardDTO;
import com.example.ex03.dto.PageRequestDTO;
import com.example.ex03.dto.PageResultDTO;
import com.example.ex03.entity.Board;
import com.example.ex03.entity.Member;
import com.example.ex03.repository.BoardRepository;
import com.example.ex03.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        boardRepository.save(board);

        return board.getBno();
    }


    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        //Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = boardRepository.searchPage(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Function<Object[], BoardDTO> fn = (entity -> {

            Object[] entityArr = entity;
            BoardDTO boardDTO = entityToDto((Board) entityArr[0], (Member) entityArr[1], (Long) entityArr[2]);
            return boardDTO;
        });

        return new PageResultDTO<>(result, fn);
    }


    @Override
    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        Board board = (Board) arr[0];
        Member member = (Member) arr[1];
        Long replyCount = (long) arr[2];

        return entityToDto(board, member, replyCount);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {

        replyRepository.deleteByBoard(bno);
        boardRepository.deleteById(bno);
    }

    @Transactional
    @Override
    public void modify(BoardDTO dto) {

        Optional<Board> result = boardRepository.findById(dto.getBno());

        if(result.isPresent()){
            Board board = result.get();
            board.changeTitle(dto.getTitle());
            board.changeContent(dto.getContent());

            boardRepository.save(board);
        }
    }

}
