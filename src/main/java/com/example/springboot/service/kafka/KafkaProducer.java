package com.example.springboot.service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Long> kafkaTemplate;

    public void sendMessage(String message, long postNo) {
        System.out.printf("Producer message : %s%n", message);
        kafkaTemplate.send("viewCount", postNo);
    }

}
