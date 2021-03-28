package com.example.ex03.movie.service;

import com.example.ex03.movie.dto.ReviewDTO;
import com.example.ex03.movie.entity.Movie;
import com.example.ex03.movie.entity.Review;
import com.example.ex03.movie.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getListOfMovie(Long mno) {
        List<Review> result = reviewRepository.findByMovie(Movie.builder().mno(mno).build());
        return result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {
        Review review = dtoToEntity(reviewDTO);
        reviewRepository.save(review);
        return review.getReviewnum();
    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {
        Optional<Review> result = reviewRepository.findById(reviewDTO.getReviewnum());
        if(result.isPresent()){
            Review review = result.get();
            review.changeGrade(reviewDTO.getGrade());
            review.changeText(reviewDTO.getText());
            reviewRepository.save(review);
        }
    }
}
