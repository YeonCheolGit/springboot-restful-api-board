package com.example.springboot.service.kafka;


import com.example.springboot.DTO.kafka.PostViewCountDTO;
import com.example.springboot.DTO.kafka.SearchedTitleDTO;
import com.example.springboot.config.kafka.PostViewProducerConfig;
import com.example.springboot.config.kafka.SearchTitleProducerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class KafkaProducerService {
    private final PostViewProducerConfig postViewProducerConfig;
    private final SearchTitleProducerConfig searchTitleProducerConfig;
    private static final String topic_viewCount = "viewCount";
    private static final String topic_saveSearchTitle = "searchTitle";

    @Async
    public void sendPostNo(PostViewCountDTO postViewCountDTO) {
        System.out.printf("Producer message : %s%n", postViewCountDTO.getPostNo());

        postViewProducerConfig.postViewDTOKafkaTemplate()
                .send(topic_viewCount, postViewCountDTO);
    }


    @Async
    public void sendSearchTitle(SearchedTitleDTO title) {
        System.out.printf("Producer message : %s%n", title.getSearched_title());

        searchTitleProducerConfig.searchedTitleDTO_ProducerTemplate()
                .send(topic_saveSearchTitle, title);
    }
}
