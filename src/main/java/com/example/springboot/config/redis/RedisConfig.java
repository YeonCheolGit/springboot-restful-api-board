package com.example.springboot.config.redis;

import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private int redisPort;

    @PostConstruct
    protected void init() {
        redisHost = Base64.getEncoder().encodeToString(redisHost.getBytes());
    }

//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .readFrom(ReadFrom.REPLICA_PREFERRED)
//                .build();
//
//        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
//
////        RedisClusterConfiguration serverConfig = new RedisClusterConfiguration()
////                .clusterNode("localhost", 6000)
////                .clusterNode("localhost", 6001)
////                .clusterNode("localhost", 6002)
////                .clusterNode("localhost", 7001)
////                .clusterNode("localhost", 7002)
////                .clusterNode("localhost", 7003);
//
//        return new LettuceConnectionFactory(serverConfig, clientConfig);
//    }

    @Bean
    public LettuceConnectionFactory sentinelConfigFactory() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();

        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("127.0.0.1", 5000)
                .sentinel("127.0.0.1", 5001)
                .sentinel("127.0.0.1", 5002);

        return new LettuceConnectionFactory(sentinelConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }
}
