package com.example.ex03.repository;

import com.example.ex03.movie.entity.Movie;
import com.example.ex03.movie.entity.MovieImage;
import com.example.ex03.movie.repository.MovieImageRepository;
import com.example.ex03.movie.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;


    @Commit
    @Transactional
    @Test
    public void insertMovies(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                                .title("Movie..."+i)
                                .build();
            System.out.println("--------------------------------------");
            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) + 1;

            for(int j = 0; j < count; j++){
                MovieImage movieImage = MovieImage.builder()
                                                    .uuid(UUID.randomUUID().toString())
                                                    .imgName("test"+j+".jpg")
                                                    .movie(movie)
                                                    .build();
                movieImageRepository.save(movieImage);
            }
            System.out.println("=========================================");
        });
    }


    @Test
    public void testGetList(){
        Page<Object[]> result = movieRepository.getListPage(PageRequest.of(0, 10, Sort.by("mno").descending()));

        result.stream().forEach(arr -> {
            System.out.println(Arrays.toString(arr));
        });
    }


    @Test
    public void testGetMovieWithAll(){
        List<Object[]> result = movieRepository.getMovieWithAll(93L);

        result.stream().forEach(arr -> {
            System.out.println(Arrays.toString(arr));
        });
    }
}
