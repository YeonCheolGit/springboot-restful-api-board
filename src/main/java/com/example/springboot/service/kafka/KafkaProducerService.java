package com.example.springboot.service.kafka;


import com.example.springboot.DTO.kafka.PostViewCountDTO;
import com.example.springboot.DTO.kafka.SearchedTitleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class KafkaProducerService {
    private final KafkaTemplate<String, PostViewCountDTO> postViewKafkaTemplate;
    private final KafkaTemplate<String, SearchedTitleDTO> searchedKafkaTemplate;
    private static final String topic_viewCount = "viewCount";
    private static final String topic_saveSearchTitle = "searchTitle";

    @Async
    public void sendPostNo(PostViewCountDTO postViewCountDTO) {
        System.out.printf("Producer message : %s%n", postViewCountDTO.getPostNo());
        postViewKafkaTemplate.send(topic_viewCount, null, postViewCountDTO);
    }

    @Async
    public void sendSearchTitle(SearchedTitleDTO title) {
        System.out.printf("Producer message : %s%n", title.getSearched_title());
        searchedKafkaTemplate.send(topic_saveSearchTitle, title);
    }
}
