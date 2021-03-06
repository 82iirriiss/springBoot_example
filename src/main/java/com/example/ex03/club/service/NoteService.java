package com.example.ex03.club.service;

import com.example.ex03.club.dto.NoteDTO;
import com.example.ex03.club.entity.ClubMember;
import com.example.ex03.club.entity.Note;

import java.util.List;

public interface NoteService {

    Long register(NoteDTO noteDTO);

    NoteDTO get(Long num);

    void modify(NoteDTO noteDTO);

    void remove(Long num);

    List<NoteDTO> getAllWithWriter(String writerEmail);

    default Note dtoToEntity(NoteDTO noteDTO){
        return Note.builder()
                    .num(noteDTO.getNum())
                    .title(noteDTO.getTitle())
                    .content(noteDTO.getContent())
                    .writer(ClubMember.builder().email(noteDTO.getWriterEmail()).build())
                    .build();
    }

    default NoteDTO entityToDto(Note note){
        return NoteDTO.builder()
                        .num(note.getNum())
                        .title(note.getTitle())
                        .content(note.getContent())
                        .writerEmail(note.getWriter().getEmail())
                        .regDate(note.getRegDate())
                        .modDate(note.getModDate())
                        .build();
    }
}
