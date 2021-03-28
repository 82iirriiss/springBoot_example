package com.example.ex03.movie.service;

import com.example.ex03.movie.dto.ReviewDTO;
import com.example.ex03.movie.entity.Movie;
import com.example.ex03.movie.entity.MovieMember;
import com.example.ex03.movie.entity.Review;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getListOfMovie(Long mno);

    Long register(ReviewDTO reviewDTO);

    void remove(Long reviewnum);

    void modify(ReviewDTO reviewDTO);

    default Review dtoToEntity(ReviewDTO reviewDTO){
        Movie movie = Movie.builder().mno(reviewDTO.getMno()).build();
        MovieMember member = MovieMember.builder().mid(reviewDTO.getMid())
                                                    .email(reviewDTO.getEmail())
                                                    .nickname(reviewDTO.getNickname())
                                                    .build();
        Review review = Review.builder().reviewnum(reviewDTO.getReviewnum())
                                        .grade(reviewDTO.getGrade())
                                        .text(reviewDTO.getText())
                                        .movie(movie)
                                        .movieMember(member)
                                        .build();

        return review;
    }

    default ReviewDTO entityToDto(Review review){
        ReviewDTO reviewDTO = ReviewDTO.builder()
                                        .mno(review.getMovie().getMno())
                                        .reviewnum(review.getReviewnum())
                                        .mid(review.getMovieMember().getMid())
                                        .nickname(review.getMovieMember().getNickname())
                                        .email(review.getMovieMember().getEmail())
                                        .grade(review.getGrade())
                                        .text(review.getText())
                                        .modDate(review.getModDate())
                                        .regDate(review.getRegDate())
                                        .build();
        return reviewDTO;
    }
}
