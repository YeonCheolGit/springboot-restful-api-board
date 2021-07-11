package com.example.springboot.service.reply;

import com.example.springboot.DTO.ParamReply;
import com.example.springboot.entity.Reply;

public interface ReplyService {

    void writeReply(long postNo, String userId, ParamReply reply);

    Reply updateReply(long replyNo, String userId, ParamReply reply);

    void deleteReply(long replyNo, String userId);
}
