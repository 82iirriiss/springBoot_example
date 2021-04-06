package com.example.ex03.security.filter;

import com.example.ex03.security.dto.ClubAuthMemeberDTO;
import com.example.ex03.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private JWTUtil jwtUtil;

    public ApiLoginFilter(String DefaultFilterProcessesUrl, JWTUtil jwtUtil){

        super(DefaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        log.info("......................ApiLoginFilter........................");
        log.info("attemptAuthentication");

        String email = request.getParameter("email");
        String pw = request.getParameter("pw");

//        인증을 하기 위해서는, AuthenticationManager를 사용해서 동작해야 하며,
//        AuthenticationManager는 파라미터와 리턴값이 모두 Authentication 타입이다.
//        Authentication타입의 객체로는 'xxxToken' 이라는 네이밍룰을 사용한다.
//        UsernamePasswordAuthenticationToken은, 그중 가장 간편하게 사용할 수 있는 객체이다.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, pw);

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException
    {
        log.info("---------------ApiLoginFilter--------------------");
        log.info("successfulAuthentication: " + authResult);
        log.info(authResult.getPrincipal());

        //email address
        String email =((ClubAuthMemeberDTO) authResult.getPrincipal()).getUsername();
        String token = null;
        try{
            token = jwtUtil.generateToken(email);
            // 로그인 성공 시, JWT 토큰을 발행함.
            response.setContentType(("text/plain"));
            response.getOutputStream().write(token.getBytes());

            log.info(token);
            // http://localhost:8080/api/login?email=user90@example.com&pw=1111
            // eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MTc3MTM2ODcsImV4cCI6MTYyMDMwNTY4Nywic3ViIjoidXNlcjkwQGV4YW1wbGUuY29tIn0.L9S42VkwCLSjIqygPZ7a6yzMbnHWGn1vWdweVsuqgcA
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
