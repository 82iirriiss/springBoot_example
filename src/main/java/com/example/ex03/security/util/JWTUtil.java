package com.example.ex03.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {

    private String secretKey = "example12345678";

    //1month
    private long expire = 60 * 24 * 30;

//    JWT 토큰 생성 : 암호화 알고리즘 + 키를 이용한 signature + 키/값 쌍으로 이루어진 payload('sub':email)
    public String generateToken(String content) throws Exception{

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                .claim("sub", content)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

//    JWT 문자열을 검증하는 역할. payload에 입력한 값을 확인할 수 있다.
    public String validateAndExtract(String tokenStr)throws Exception{
        String contentValue = null;

        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(tokenStr);

            log.info(defaultJws);
            log.info(defaultJws.getBody().getClass());

            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();
            log.info("--------------------------------");

            contentValue = claims.getSubject();
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;
        }
        return contentValue;
    }
}
