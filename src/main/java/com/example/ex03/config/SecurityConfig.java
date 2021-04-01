package com.example.ex03.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //1. PasswordEncoder 객체 생성
    //2. Authentication Manager 설정 : configure()
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

// 더이상 사용하지 않는다. UserDetailsService를 구현한 ClubUserDetailsService 가 빈으로 등록되면서, 자동으로 UserDetailsService로 인식한다.
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
//        auth.inMemoryAuthentication().withUser("user1")
//                                    .password("153da303-082b-446c-81b8-f2e17d6ceb4f")
//                                    .roles("USER");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/guestbook/list").permitAll()
                .antMatchers("/movie/list").hasRole("USER");

        //인증/인가 실패 시, 로그인 화면 보여주기
        //그밖에 사용자 설정을 위한 loginPage(), loginProcessUrl(), defaultSuccessUrl(), failureUrl() 등이 존재함
        http.formLogin();

        //csrf 비활성화 : 외부에서 REST 방식을 이용할 수 있게 하는 경우,
        //http.csrf().disable();

        //로그아웃 사용시 주의사항: CSRF 사용할 경우, 반드시 POST 방식으로만 로그아웃을 처리함.
        // 사용자가 별도의 로그아웃 설정을 추가할 수 있도록, logoutUrl(), logoutSuccessUrl() 함수 제공.
        // 로그아웃 시, 세션무효화와 쿠키에서 세션의 삭제를 위해 invalidatedHttpSession()과 deleteCookies()를 제공.
        http.logout();

        http.csrf().disable();

        http.oauth2Login();
    }
}
