package com.example.ex03.club.controller;

import com.example.ex03.club.dto.NoteDTO;
import com.example.ex03.club.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes/")
public class NoteController {

    private final NoteService noteservice;

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO){
        Long num = noteservice.register(noteDTO);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    @GetMapping(value = "/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDTO> get(@PathVariable("num") Long num){
        return new ResponseEntity<>(noteservice.get(num), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteDTO>> getList(String email){
        return new ResponseEntity<>(noteservice.getAllWithWriter(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("num") Long num){
        noteservice.remove(num);
        return new ResponseEntity<>("removed", HttpStatus.OK);
    }

    @PutMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@PathVariable("num")Long num, @RequestBody NoteDTO noteDTO){
        noteservice.modify(noteDTO);
        return new ResponseEntity<>("modified", HttpStatus.OK);
    }
}
