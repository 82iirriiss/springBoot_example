package com.example.ex03.club.service;

import com.example.ex03.club.dto.NoteDTO;
import com.example.ex03.club.entity.ClubMember;
import com.example.ex03.club.entity.Note;
import com.example.ex03.club.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{

    private final NoteRepository repository;

    @Override
    public Long register(NoteDTO noteDTO) {
        Note note = dtoToEntity(noteDTO);
        repository.save(note);
        return note.getNum();
    }

    @Override
    public NoteDTO get(Long num) {
        Optional<Note> result = repository.findById(num);
        if(result.isPresent()) return entityToDto(result.get());
        return null;
    }

    @Override
    public void modify(NoteDTO noteDTO) {
        Long num = noteDTO.getNum();
        Optional<Note> result = repository.findById(num);
        if(result.isPresent()){
            Note note = result.get();
            note.changeTitle(noteDTO.getTitle());
            note.changeContent(noteDTO.getContent());
            repository.save(note);
        }
    }

    @Override
    public void remove(Long num) {
        repository.deleteById(num);
    }

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {
        return repository.getList(writerEmail).stream().map(note -> {
            return NoteDTO.builder().num(note.getNum())
                                    .title(note.getTitle())
                                    .content(note.getContent())
                                    .writerEmail(note.getWriter().getEmail())
                                    .modDate(note.getModDate())
                                    .regDate(note.getRegDate())
                                    .build();
        }).collect(Collectors.toList());
    }
}
