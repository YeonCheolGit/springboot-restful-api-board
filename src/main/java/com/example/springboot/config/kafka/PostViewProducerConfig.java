package com.example.springboot.config.kafka;

import com.example.springboot.DTO.kafka.PostViewCountDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class PostViewProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public Map<String,Object> postViewProducerConfigs() {
        return CommonJsonSerializer.getStringObjectMap(bootstrapServer);
    }

    @Bean
    public ProducerFactory<String, PostViewCountDTO> postViewCountDTOProducerFactory() {
        return new DefaultKafkaProducerFactory<>(postViewProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, PostViewCountDTO> postViewDTOKafkaTemplate() {
        return new KafkaTemplate<>(postViewCountDTOProducerFactory());
    }
}
