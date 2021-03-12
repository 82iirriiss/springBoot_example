package com.example.ex03.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyDTO {
    private Long rno;

    private String text;

    private String replyer;

    private Long bno;

    private LocalDateTime regDate, modDate;
}
