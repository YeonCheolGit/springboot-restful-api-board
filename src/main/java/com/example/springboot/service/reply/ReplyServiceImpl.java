package com.example.springboot.service.reply;

import com.example.springboot.DTO.ParamReply;
import com.example.springboot.DTO.reply.ReplyRequestDTO;
import com.example.springboot.advice.exception.FindAnyFailException;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import com.example.springboot.respository.PostRepository;
import com.example.springboot.respository.ReplyRepository;
import com.example.springboot.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 업데이트 dirty checking 위한 메서드 입니다.
    private Reply getReply(long replyNo) {
        return Optional.ofNullable(replyRepository.findByReplyNo(replyNo)).orElseThrow(FindAnyFailException::new);
    }

    private Post getPost(long postNo) {
        return postRepository.findByPostNo(postNo).orElseThrow(FindAnyFailException::new);
    }

    @Transactional
    @Override
    public void writeReply(long postNo, String userId, ParamReply paramReply) {
        Post post = getPost(postNo);

        ReplyRequestDTO replyRequestDTO = new ReplyRequestDTO().builder()
                .postNo(post)
                .userNo(userRepository.findByUserId(userId).orElseThrow(FindAnyFailException::new))
                .author(paramReply.getAuthor())
                .content(paramReply.getContent())
                .build();

        replyRepository.save(replyRequestDTO.toEntity());
    }

    @Transactional
    @Override
    public Reply updateReply(long replyNo, String userId, ParamReply paramReply) {
        Reply reply = getReply(replyNo);

        if (!reply.getAuthor().equals(userId))
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        reply.setUpdate(paramReply.getContent());
        return reply;
    }

    @Transactional
    @Override
    public void deleteReply(long replyNo, String userId) {
        Reply reply = getReply(replyNo);

        if (!reply.getAuthor().equals(userId))
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        replyRepository.delete(reply);
    }
}
