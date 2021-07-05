package com.example.springboot.service.reply;

import com.example.springboot.DTO.ParamReply;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import org.springframework.stereotype.Service;

public interface ReplyService {

    void writeReply(String userId, long postNo, ParamReply reply);
}
