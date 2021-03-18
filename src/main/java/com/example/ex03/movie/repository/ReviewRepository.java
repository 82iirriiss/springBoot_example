package com.example.ex03.movie.repository;

import com.example.ex03.movie.entity.Movie;
import com.example.ex03.movie.entity.MovieMember;
import com.example.ex03.movie.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"movieMember"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review r where r.movieMember = :member")
    void deleteByMovieMember(MovieMember member);
}

