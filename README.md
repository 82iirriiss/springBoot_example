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