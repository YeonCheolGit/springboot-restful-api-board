package com.example.springboot.service.reply;

import com.example.springboot.DTO.ParamReply;
import com.example.springboot.entity.Reply;
import com.example.springboot.respository.ReplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    @Override
    public void writeReply(String userId, long postNo, ParamReply reply) {
        Reply reply1 = new Reply(reply.getContent(), userId, postNo);
        replyRepository.save(reply1);
    }
}
