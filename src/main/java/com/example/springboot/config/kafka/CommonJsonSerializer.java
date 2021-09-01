package com.example.springboot.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class CommonJsonSerializer {

    static Map<String, Object> getStringObjectMap(String bootstrapServer) {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(org.springframework.kafka.support.serializer.JsonSerializer.TYPE_MAPPINGS,
                "PostViewCountDTO:com.example.springboot.DTO.kafka.PostViewCountDTO," +
                        "SearchedTitleDTO: com.example.springboot.DTO.kafka.SearchedTitleDTO");

        return props;
    }
}
