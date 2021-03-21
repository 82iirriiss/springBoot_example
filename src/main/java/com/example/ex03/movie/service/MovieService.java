package com.example.ex03.movie.service;

import com.example.ex03.common.dto.PageRequestDTO;
import com.example.ex03.common.dto.PageResultDTO;
import com.example.ex03.movie.dto.MovieDTO;
import com.example.ex03.movie.dto.MovieImageDTO;
import com.example.ex03.movie.entity.Movie;
import com.example.ex03.movie.entity.MovieImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    Long register(MovieDTO dto);

    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    default Map<String, Object> dtoToEntity(MovieDTO dto){

        Movie movie = Movie.builder().title(dto.getTitle()).build();
        Map<String, Object> map = new HashMap<>();
        map.put("movie", movie);
        List<MovieImageDTO> imageDTOList = dto.getImageDTOList();

        if(imageDTOList!=null && imageDTOList.size() > 0){
             List<MovieImage> movieImageList = imageDTOList.stream().map(imageDTO -> {
                 return MovieImage.builder()
                                    .movie(movie)
                                    .imgName(imageDTO.getImgName())
                                    .path(imageDTO.getPath())
                                    .uuid(imageDTO.getUuid()).build();
            }).collect(Collectors.toList());
            map.put("movieImage", movieImageList);
        }

        return map;
    }

    default MovieDTO entitiesToDto(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt){

        List<MovieImageDTO> imageDTOList = movieImages.stream().map(movieImage ->
                MovieImageDTO.builder().path(movieImage.getPath())
                                        .uuid(movieImage.getUuid())
                                        .imgName(movieImage.getImgName())
                                        .build()
        ).collect(Collectors.toList());

        return MovieDTO.builder().mno(movie.getMno())
                            .title(movie.getTitle())
                            .imageDTOList(imageDTOList)
                            .avg(avg)
                            .reviewCnt(reviewCnt.intValue())
                            .regDate(movie.getRegDate())
                            .modDate(movie.getModDate())
                            .build();
    }
}
