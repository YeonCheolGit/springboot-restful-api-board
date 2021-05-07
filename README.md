# SpringBoot-Restful-API-Board
![Generic badge](https://img.shields.io/badge/jdk-11-red.svg)
![Generic badge](https://img.shields.io/badge/springboot-2.4.4-green.svg)
![Generic badge](https://img.shields.io/badge/jjwt-0.9.1-blue.svg)
![Generic badge](https://img.shields.io/badge/lombok-1.18.18-yellow.svg)

## application.yml
```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: [url]
    username: [name]
    password: [password]
  jpa:
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    database: mysql
    properties.hibernate.hbm2ddl.auto: none
    hibernate:
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
  jwt:
    secret: [key]
    social:
      kakao:
        client_id: [private kakao client_id]
        redirect: /social/login/kakao
        url:
          login: https://kauth.kakao.com/oauth/authorize
          token: https://kauth.kakao.com/oauth/token
          profile: https://kapi.kakao.com/v2/user/me
    url:
      base: http://localhost:8080
    freemarker:
      template-loader-path: classpath:/templates/
      prefix: /social/
      suffix: .ftl
```

## Getting Started
```
$ git clone https://github.com/YeonCheolGit/springboot-board-backend.git
```



