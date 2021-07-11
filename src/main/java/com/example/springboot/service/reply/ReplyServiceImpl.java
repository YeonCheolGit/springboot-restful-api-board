package com.example.springboot.service.reply;

import com.example.springboot.DTO.ParamReply;
import com.example.springboot.DTO.post.PostDTO;
import com.example.springboot.DTO.reply.ReplyDTO;
import com.example.springboot.advice.exception.FindAnyFailException;
import com.example.springboot.entity.Reply;
import com.example.springboot.respository.PostRepository;
import com.example.springboot.respository.ReplyRepository;
import com.example.springboot.respository.UserRepository;
import com.example.springboot.service.ToDTO;
import com.example.springboot.service.ToEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ToDTO toDTO;
    private final ToEntity toEntity;

    private ReplyDTO getReply(long replyNo) {
        return toDTO.toReplyDTO(replyRepository.findByReplyNo(replyNo).orElseThrow(FindAnyFailException::new));
    }

    public PostDTO getPost(long postNo) {
        return toDTO.toSinglePostDTO(postRepository.findByPostNo(postNo).orElseThrow(FindAnyFailException::new));
    }

    @Transactional
    @Override
    public void writeReply(long postNo, String userId, ParamReply paramReply) { // 댓글 작성 합니다.
        PostDTO postDTO = getPost(postNo);

        ReplyDTO replyDTO = new ReplyDTO().builder()
                .postNo(toEntity.toPostEntity(postDTO))
                .userNo(userRepository.findByUserId(userId).orElseThrow(FindAnyFailException::new))
                .author(paramReply.getAuthor())
                .content(paramReply.getContent())
                .build();

        replyRepository.save(toEntity.toReplyEntity(replyDTO));
    }

    @Transactional
    @Override
    public Reply updateReply(long replyNo, String userId, ParamReply paramReply) { // 댓글 수정 합니다.
        ReplyDTO replyDTO = getReply(replyNo);

        if (!replyDTO.getAuthor().equals(userId))
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        replyDTO.setUpdate(paramReply.getContent());

        return replyRepository.save(toEntity.toReplyEntity(replyDTO));
    }

    @Transactional
    @Override
    public void deleteReply(long replyNo, String userId) { // 댓글 삭제 합니다.
        ReplyDTO replyDTO = getReply(replyNo);

        if (!replyDTO.getAuthor().equals(userId))
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        replyRepository.delete(toEntity.toReplyEntity(replyDTO));
    }
}
