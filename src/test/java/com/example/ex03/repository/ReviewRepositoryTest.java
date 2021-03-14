package com.example.ex03.repository;

import com.example.ex03.movie.entity.Movie;
import com.example.ex03.movie.entity.MovieMember;
import com.example.ex03.movie.entity.Review;
import com.example.ex03.movie.repository.MovieMemberRepository;
import com.example.ex03.movie.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository repository;

    @Test
    public void insertReviews(){

        IntStream.rangeClosed(1, 200).forEach(i->{

            Movie movie = Movie.builder().mno((long)(Math.random() * 100) + 1).build();

            MovieMember member = MovieMember.builder().mid((long)(Math.random() * 100) + 1).build();

            Review review = Review.builder()
                                    .grade((int)(Math.random() * 5) + 1)
                                    .text("이 영화에 대한 느낌...."+i)
                                    .movie(movie)
                                    .movieMember(member).build();
            repository.save(review);
        });
    }
}
