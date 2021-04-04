package com.example.ex03.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemeberDTO extends User implements OAuth2User {

    public ClubAuthMemeberDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    private String email;

    private String name;

    private String password;

    private boolean fromSocial;

    private Map<String, Object> attr;

    //ClubUserDetailService 에서 사용하는 생성자
    public ClubAuthMemeberDTO(String username,
                              String password,
                              String name,
                              boolean fromSocial,
                              Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.email = username; //id와 같은 역할.
        this.name = name;
        this.fromSocial = fromSocial;
    }

    public ClubAuthMemeberDTO(String username,
                              String password,
                              boolean fromSocial,
                              Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.email = username; //id와 같은 역할.
        this.password = password;
        this.fromSocial = fromSocial;
    }

    // ClubOAuthUserDetailService 에서 사용하는 생성자
    public ClubAuthMemeberDTO(
            String username,
            String password,
            boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attr ){
        this(username, password, fromSocial, authorities);
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
