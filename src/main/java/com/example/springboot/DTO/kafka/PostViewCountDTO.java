package com.example.springboot.DTO.kafka;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostViewCountDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private long postNo;
}
