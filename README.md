### 추가할 라이브러리

- Spring Boot DevTools
- Lombok
- Spring Web
- Thymeleaf
- Spring Data JPA
- thymeleaf 시간처리 라이브러리 (build.gradle에 직접 추가)

### application.properties 설정 추가 (JDBC, JPA, Thymeleaf)

```
 spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
 spring.datasource.url=jdbc:mariadb://localhost:3306/bootex
 spring.datasource.username=bookuser
 spring.datasource.password=bookuser
 
 spring.jpa.hibernate.ddl-auto=update
 spring.jpa.properties.hibernate.format_sql=true
 spring.jpa.show-sql=true
 
 spring.thymeleaf.cache=false
```

### JPQL을 위해 build.gradle에 Querydsl 설정 추가하기

```
plugins {
    (생략)
    id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
}

dependencies {
    (생략)
    // https://mvnrepository.com/artifact/com.querydsl/querydsl-jpa
    implementation group: 'com.querydsl', name: 'querydsl-jpa', version: '4.3.1'
}

//querydsl 관련 추가
def querydslDir = "$buildDir/generated/querydsl"

querydsl {
    jpa = true
    querydslSourcesDir = querydslDir
}

sourceSets {
    main.java.srcDir querydslDir
}

configurations {
    querydsl.extendsFrom compileClasspath
}

compileQuerydsl{
    options.annotationProcessorPath = configurations.querydsl
}
```

- 설정 후, compileQuerydsl(설정의 마지막 부분) 왼쪽의 ▶ 을 클릭하여, Task를 실행한다.
- 프로젝트의 'build>generated>querydsl' 하위에 엔티티이름에 Q 가 붙은 클래스들이 생성될 것 이다.

### 엔티티 설계
- regDate나 modDate 컬럼의 경우, 코딩하지 않아도 자동으로 입력되도록 설계한 <font color="yellow">BaseEntity</font>를 모든 엔티티에서 상속한다.
- Querydsl과 JPQL을 사용하는 검색/정렬 처리는 xxxsearchRepository, xxxsearchRepositoryImpl 을 생성하여 ***Repository에서 상속한다.
 
### Security 구성
1. 인증을 위해 'AuthenticationManager'가 작업을 하는데, 실제로는 UsernamePasswordAuthenticationToken과 같이 토큰이라는 객체로 만들어서 전달한다.
2. 실제 인증을 위한 데이터를 가져오는 AuthenticationProvider는, 내부적으로 UserDetailsService 객체를 이용한다.
3. UserDetails 인터페이스
    - getAuthorities() : 사용자가 가지는 권한에 대한 정보
    - getPassword() : 인증을 마무리하기 위한 패스워드 정보
    - getUsername() : 인증에 필요한 아이디
    - 계정만료여부
    - 계정 잠김 여부
* 스프링 시큐리티는 회원/계정에 대해서, User 라는 명칭을 사용하므로, 오해 없도록 한다.
* 회원아이디라는 용어대신 username 이라는 용서를 사용하는데, 이를 아이디와 동일시 하면 된다.
* username과 password를 동시에 사용하지 않는다. username으로 존재유무를 먼저 가져오고, 이후에 password가 틀리면, 'Bad Cridential' 결과를 생성한다.
