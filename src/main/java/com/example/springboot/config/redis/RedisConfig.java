package com.example.springboot.config.redis;

import io.lettuce.core.ReadFrom;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;

@Configuration
@AllArgsConstructor
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();

        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration("localhost", 6379);
//        RedisClusterConfiguration serverConfig = new RedisClusterConfiguration()
//                .clusterNode("localhost", 6000)
//                .clusterNode("localhost", 6001)
//                .clusterNode("localhost", 6002)
//                .clusterNode("localhost", 7001)
//                .clusterNode("localhost", 7002)
//                .clusterNode("localhost", 7003);

        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }
}
