package com.example.springboot.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParamReply { // 댓글 조회/작성을 위한 파라미터 정의 객체 입니다.

    @ApiModelProperty(value = "작성자", required = true)
    private String author;

    @ApiModelProperty(value = "내용", required = true)
    private String content;

}
