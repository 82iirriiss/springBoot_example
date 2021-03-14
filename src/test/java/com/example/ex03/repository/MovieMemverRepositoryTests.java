package com.example.ex03.repository;

import com.example.ex03.movie.entity.MovieMember;
import com.example.ex03.movie.repository.MovieMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MovieMemverRepositoryTests {

    @Autowired
    private MovieMemberRepository memberRepository;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            MovieMember member = MovieMember.builder()
                                            .email("r"+i+"@yunmi.org")
                                            .pw("1111")
                                            .nickname("reviewer"+i).build();
            memberRepository.save(member);
        });
    }
}
