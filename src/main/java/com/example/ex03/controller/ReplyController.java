package com.example.ex03.controller;

import com.example.ex03.dto.ReplyDTO;
import com.example.ex03.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService service;


    @GetMapping(value= "/board/{bno}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno){
        return new ResponseEntity<>(service.getList(bno), HttpStatus.OK);
    }


    @PostMapping(value = "",
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
        Long rno = service.register(replyDTO);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }


    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
        service.remove(rno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @PutMapping("")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO){
        service.modify(replyDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
