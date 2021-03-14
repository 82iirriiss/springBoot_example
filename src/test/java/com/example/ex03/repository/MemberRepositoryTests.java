package com.example.ex03.repository;

import com.example.ex03.board.entity.Member;
import com.example.ex03.board.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                                    .email("user"+i+"@aaa.com")
                                    .name("USER"+i)
                                    .password("1111")
                                    .build();
            memberRepository.save(member);
        });
    }
}
