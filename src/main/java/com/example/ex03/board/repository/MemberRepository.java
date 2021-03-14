package com.example.ex03.board.repository;

import com.example.ex03.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
