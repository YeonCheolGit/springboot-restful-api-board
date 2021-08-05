package com.example.springboot.service.reply;

import com.example.springboot.DTO.ParamReply;
import com.example.springboot.DTO.post.SinglePostDTO;
import com.example.springboot.DTO.reply.ReplyDTO;
import com.example.springboot.advice.exception.FindAnyFailException;
import com.example.springboot.entity.Reply;
import com.example.springboot.respository.PostRepository;
import com.example.springboot.respository.ReplyRepository;
import com.example.springboot.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private ReplyDTO getReply(long replyNo) {
        return new ReplyDTO().toReplyDTO(replyRepository.findByReplyNo(replyNo).orElseThrow(FindAnyFailException::new));
    }

    public SinglePostDTO getPost(long postNo) {
        return new SinglePostDTO().requestSinglePostDTO(postRepository.findByPostNo(postNo).orElseThrow(FindAnyFailException::new));
    }

    @Transactional
    public void writeReply(long postNo, String userId, ParamReply paramReply) { // 댓글 작성 합니다.
        SinglePostDTO singlePostDTO = getPost(postNo);

        ReplyDTO replyDTO = ReplyDTO.builder()
                .post(singlePostDTO.toPostEntity(singlePostDTO))
                .user(userRepository.findByUserId(userId).orElseThrow(FindAnyFailException::new))
                .author(paramReply.getAuthor())
                .content(paramReply.getContent())
                .build();

        replyRepository.save(replyDTO.toReplyEntity(replyDTO));
    }

    @Transactional
    public Reply updateReply(long replyNo, String userId, ParamReply paramReply) { // 댓글 수정 합니다.
        ReplyDTO replyDTO = getReply(replyNo);

        if (!replyDTO.getAuthor().equals(userId))
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        replyDTO.setUpdate(paramReply.getContent());

        return replyRepository.save(replyDTO.toReplyEntity(replyDTO));
    }

    @Transactional
    public void deleteReply(long replyNo, String userId) { // 댓글 삭제 합니다.
        ReplyDTO replyDTO = getReply(replyNo);

        if (!replyDTO.getAuthor().equals(userId))
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        replyRepository.delete(replyDTO.toReplyEntity(replyDTO));
    }
}
