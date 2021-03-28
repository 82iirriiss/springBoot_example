package com.example.ex03.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long reviewnum;
    // movie mno
    private Long mno;
    //member mid
    private Long mid;

    private String nickname;

    private String email;

    private int grade;

    private String text;

    private LocalDateTime regDate, modDate;
}
