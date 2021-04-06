package com.example.ex03.security;

import com.example.ex03.security.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JWTTests {

    private JWTUtil jwtUtil;

    @BeforeEach
    public void testBefore(){

        // 스프링을 사용한 테스트가 아니므로, 주입하지 않고 new 를 이용하여 객체 생성해 줌.
        System.out.println("testBefore..........");
        jwtUtil = new JWTUtil();
    }

    // JWT 문자열 생성 확인
    // 확인은, https://jwt.io 사이트에서 비밀키를 채운 후 현재 생성된 암호화 문자열을 붙여 넣으면, 분석이 됨.
    @Test
    public void testEncode() throws Exception{
        String email = "user95@example.com";

        String str = jwtUtil.generateToken(email);

        System.out.println(str);
        //eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MTc3MTMzMTEsImV4cCI6MTYyMDMwNTMxMSwic3ViIjoidXNlcjk1QGV4YW1wbGUuY29tIn0.LqhWKsI5iHpMLP3xA56hdtfKEh1qVVvU-MIM971U6Q8
    }

    // 만들어진 JWT 문자열을 디코딩 하면, 내가 실제로 입력 했던 이메일이 확인 되는지 확인
    // 유효기간 확인
    @Test
    public void testValidate() throws Exception {

        String email = "user95@example.com";

        String str = jwtUtil.generateToken(email);

        Thread.sleep(5000);

        String resultEmail = jwtUtil.validateAndExtract(str);

        System.out.println(resultEmail);
    }
}
