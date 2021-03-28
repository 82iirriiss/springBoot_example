package com.example.ex03.movie.controller;

import com.example.ex03.movie.dto.ReviewDTO;
import com.example.ex03.movie.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno){
        List<ReviewDTO> result = reviewService.getListOfMovie(mno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/{mno}")
    public ResponseEntity<Long> register(@PathVariable("mno") Long mno, @RequestBody ReviewDTO reviewDTO){
        Long reviewnum = reviewService.register(reviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }


    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> remove(@PathVariable("reviewnum") Long reviewnum){
        reviewService.remove(reviewnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modify(@PathVariable("reviewnum") Long reviewnum, @RequestBody ReviewDTO reviewDTO){

        reviewService.modify(reviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }
}
