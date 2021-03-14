package com.example.ex03.movie.repository;

import com.example.ex03.movie.entity.MovieMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieMemberRepository extends JpaRepository<MovieMember, Long> {
}
