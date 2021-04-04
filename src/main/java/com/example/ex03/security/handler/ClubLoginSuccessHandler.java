package com.example.ex03.security.handler;

import com.example.ex03.security.dto.ClubAuthMemeberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder;

    public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        log.info("------------------------------------------");
        log.info("onAuthenticationSucess!............");

        ClubAuthMemeberDTO authMember = (ClubAuthMemeberDTO) authentication.getPrincipal();
        // 로그인 사용자가, 소셜로그인을 하였고, 비밀번호가 초기설정인 1111 인 경우,
        // 사용자 정보 수정 페이지로 리다이렉트 처리 해 줌.
        boolean fromSocial = authMember.isFromSocial();
        log.info("Need Modify Member?" + fromSocial);

        boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());

        if(fromSocial && passwordResult){
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");

        }
    }
}
