package com.example.ex03.movie.service;

import com.example.ex03.common.dto.PageRequestDTO;
import com.example.ex03.common.dto.PageResultDTO;
import com.example.ex03.movie.dto.MovieDTO;
import com.example.ex03.movie.entity.Movie;
import com.example.ex03.movie.entity.MovieImage;
import com.example.ex03.movie.repository.MovieImageRepository;
import com.example.ex03.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;

    @Override
    @Transactional
    public Long register(MovieDTO dto) {

        Map<String, Object> map = dtoToEntity(dto);
        Movie movie = (Movie)map.get("movie");
        movieRepository.save(movie);
        List<MovieImage> movieImageList = (List<MovieImage>) map.get("movieImage");
        movieImageList.stream().forEach(movieImage -> {
            movieImageRepository.save(movieImage);
        });
        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {

        Page<Object[]> result = movieRepository.getListPage(requestDTO.getPageable(Sort.by("mno").descending()));
        Function<Object[], MovieDTO> fn = entities -> entitiesToDto((Movie) entities[0], (List<MovieImage>) Arrays.asList((MovieImage)entities[1]), (Double) entities[2], (Long) entities[3]);
        return new PageResultDTO<>(result, fn);
    }
}
