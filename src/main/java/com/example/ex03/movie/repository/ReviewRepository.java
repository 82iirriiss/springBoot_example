package com.example.ex03.movie.repository;

import com.example.ex03.movie.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
