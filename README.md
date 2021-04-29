# SpringBoot-Restful-API-Board

## Version
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

```

## Getting Started
```
$ git clone https://github.com/YeonCheolGit/springboot-board-backend.git
```



