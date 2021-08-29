package com.example.springboot.config.kafka;

import com.example.springboot.DTO.kafka.PostViewCountDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PostViewProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServer;


    @Bean
    public Map<String,Object> postViewProducerConfigs() {
        return JsonSerializer.getStringObjectMap(bootstrapServer);

//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return props;
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
