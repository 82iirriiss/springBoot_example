package com.example.ex03.club.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    private Long num;

    private String title;

    private String content;

    private String writerEmail;

    private LocalDateTime regDate, modDate;

}
