package com.example.springboot.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// 게시물 등록, 수정 시 공통적으로 필요한 정보들 입니다
@Getter @Setter
@NoArgsConstructor
public class CommonParamPost {

    @NotBlank
    @Size(min = 2, max = 50)
    @ApiModelProperty(value = "글 작성자명", required = true)
    private String author;

    @NotEmpty
    @Size(min = 2, max = 100)
    @ApiModelProperty(value = "글 제목", required = true)
    private String title;

    @NotEmpty
    @Size(min = 2, max = 500)
    @ApiModelProperty(value = "글 내용", required = true)
    private String content;

}
