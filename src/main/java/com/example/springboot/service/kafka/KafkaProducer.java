package com.example.springboot.service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Long> kafkaTemplate;
    private static final String topicCount = "viewCount";

    @Async
    public void sendMessage(long postNo) {
        System.out.printf("Producer message : %s%n", postNo);
        kafkaTemplate.send(topicCount, postNo);
    }
}
