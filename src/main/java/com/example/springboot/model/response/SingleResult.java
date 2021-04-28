package com.example.springboot.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SingleResult<T> extends CommonResult {
    private T data;
}
