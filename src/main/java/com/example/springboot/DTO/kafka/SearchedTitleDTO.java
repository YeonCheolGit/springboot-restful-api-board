package com.example.springboot.DTO.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SearchedTitleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String searched_title;

}
