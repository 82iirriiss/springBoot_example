package com.example.ex03.movie.dto;

import lombok.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class MovieDTO {

    private Long mno;
    private String title;

    @Builder.Default
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();

    private int reviewCnt;
    private Double avg;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
