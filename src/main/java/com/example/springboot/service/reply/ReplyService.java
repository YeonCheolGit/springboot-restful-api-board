package com.example.springboot.service.reply;

import com.example.springboot.DTO.ParamReply;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import org.springframework.stereotype.Service;

public interface ReplyService {

    void writeReply(long postNo, String userId, ParamReply reply);

    Reply updateReply(long replyNo, String userId, ParamReply reply);

    void deleteReply(long replyNo, String userId);
}
