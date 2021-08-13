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
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private ReplyDTO getReply(long replyNo) {
        return new ReplyDTO().toResponseReplyDTO(replyRepository.findByReplyNo(replyNo).orElseThrow(FindAnyFailException::new));
    }

    public SinglePostDTO getPost(long postNo) {
        return new SinglePostDTO().toResponseSinglePostDTO(postRepository.findByPostNo(postNo).orElseThrow(FindAnyFailException::new));
    }

    // 특정 게시물 댓글들 가지고 옵니다.
    @Transactional
    public List<ReplyDTO> getRepliesByPostNo(long postNo) {
        return new ReplyDTO().toResponseListReplyDTO(replyRepository.getRepliesByPostNo(postNo).orElseThrow(FindAnyFailException::new));
    }

    // 특정 게시물에 댓글 작성 합니다.
    @Transactional
    public void writeReply(long postNo, String userId, ParamReply paramReply) {
        SinglePostDTO singlePostDTO = getPost(postNo);

        ReplyDTO replyDTO = ReplyDTO.builder()
                .post(singlePostDTO.toRequestPostEntity(singlePostDTO))
                .user(userRepository.findByUserId(userId).orElseThrow(FindAnyFailException::new))
                .author(paramReply.getAuthor())
                .content(paramReply.getContent())
                .build();

        replyRepository.save(replyDTO.toRequestReplyEntity(replyDTO));
    }

    // 하나의 댓글 수정 합니다.
    @Transactional
    public Reply updateReply(long replyNo, String userId, ParamReply paramReply) {
        ReplyDTO replyDTO = getReply(replyNo);

        if (!replyDTO.getAuthor().equals(userId))
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        replyDTO.setUpdate(paramReply.getContent());

        return replyRepository.save(replyDTO.toRequestReplyEntity(replyDTO));
    }

    // 하나의 댓글 삭제 합니다.
    @Transactional
    public void deleteReply(long replyNo, String userId) {
        ReplyDTO replyDTO = getReply(replyNo);

        if (!replyDTO.getAuthor().equals(userId))
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        replyRepository.delete(replyDTO.toRequestReplyEntity(replyDTO));
    }
}
