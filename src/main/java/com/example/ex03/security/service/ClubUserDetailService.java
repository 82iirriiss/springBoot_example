package com.example.ex03.security.service;


import com.example.ex03.club.entity.ClubMember;
import com.example.ex03.club.repository.ClubMemberRepository;
import com.example.ex03.security.dto.ClubAuthMemeberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

// Authentication Manager는 내부적으로 UserDetailService에서 사용자 정보를 가져온다.
@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("ClubUserDetailsService loadUserByUsername " + username);
        Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check Email or Social ");
        }

        ClubMember clubMember = result.get();
        log.info("--------------------------");
        log.info(clubMember);

        // ClubAuthMemeberDTO 가 UserDetails를 구현한 구현체이다.
        // 스프링시큐리티의 username은, 이 프로젝트 사용자 정보의 email과 매칭되고, 이것들은 id와 같은 역할을 한다.
        // ClubMember를 UserDetails로 반환하기 위해서는, ClubAuthMemberDTO로 변환하여야 한다.
        // 스프링 시큐리티의 SimpleGrantedAuthority는 'ROLE_' 로 시작한다.
        ClubAuthMemeberDTO clubAuthMemeberDTO = new ClubAuthMemeberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.getRoleSet().stream()
                                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                                        .collect(Collectors.toSet())
        );

        clubAuthMemeberDTO.setEmail(clubMember.getEmail());
        clubAuthMemeberDTO.setFromSocial(clubMember.isFromSocial());

        return clubAuthMemeberDTO;
    }
}
