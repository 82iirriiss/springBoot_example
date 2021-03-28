package com.example.ex03.repository;

import com.example.ex03.movie.entity.Movie;
import com.example.ex03.movie.entity.MovieMember;
import com.example.ex03.movie.entity.Review;
import com.example.ex03.movie.repository.MovieMemberRepository;
import com.example.ex03.movie.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MovieMemberRepository memberRepository;

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

    @Test
    public void testFindByMovie(){
        List<Review> result = repository.findByMovie(Movie.builder().mno(95L).build());

        result.stream().forEach(review ->{
            System.out.println(review.getReviewnum());
            System.out.println(review.getMovieMember().toString());
            System.out.println(review.getGrade());
            System.out.println(review.getText());
        });
    }

    @Transactional
    @Commit
    @Test
    public void testDeleteByMember(){
        repository.deleteByMovieMember(MovieMember.builder().mid(1L).build());
        memberRepository.deleteById(1L);
    }

    @Test
    public void insertReview(){

            MovieMember member = MovieMember.builder().mid(2L).build();
            Movie movie = Movie.builder().mno(102L).build();
            Review review = Review.builder()
                    .grade(5)
                    .text("test......")
                    .movie(movie)
                    .movieMember(member).build();
            Review result = repository.save(review);
            System.out.println("result:" + result.getReviewnum());
    };

}
