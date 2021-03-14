package com.example.ex03.movie.repository;

import com.example.ex03.movie.entity.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
}
