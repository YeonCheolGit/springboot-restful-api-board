package com.example.springboot.service.reply;

import com.example.springboot.DTO.ParamReply;
import com.example.springboot.DTO.post.PostDTO;
import com.example.springboot.DTO.reply.ReplyDTO;
import com.example.springboot.advice.exception.FindAnyFailException;
import com.example.springboot.entity.Reply;
import com.example.springboot.respository.PostRepository;
import com.example.springboot.respository.ReplyRepository;
import com.example.springboot.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private ReplyDTO getReply(long replyNo) {
        return replyRepository.findByReplyNo(replyNo).orElseThrow(FindAnyFailException::new).toDTO();
    }

    public PostDTO getPost(long postNo) {
        return postRepository.findByPostNo(postNo).orElseThrow(FindAnyFailException::new).toDTO(); // Entity to DTO
    }

    @Transactional
    @Override
    public void writeReply(long postNo, String userId, ParamReply paramReply) { // 댓글 작성 합니다.
        PostDTO postDTO = getPost(postNo);

        ReplyDTO replyDTO = new ReplyDTO().builder()
                .postNo(postDTO.toEntity())
                .userNo(userRepository.findByUserId(userId).orElseThrow(FindAnyFailException::new))
                .author(paramReply.getAuthor())
                .content(paramReply.getContent())
                .build();

        replyRepository.save(replyDTO.toEntity());
    }

    @Transactional
    @Override
    public Reply updateReply(long replyNo, String userId, ParamReply paramReply) { // 댓글 수정 합니다.
        ReplyDTO replyDTO = getReply(replyNo);

        if (!replyDTO.getAuthor().equals(userId))
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        replyDTO.setUpdate(paramReply.getContent());

        return replyRepository.save(replyDTO.toEntity());
    }

    @Transactional
    @Override
    public void deleteReply(long replyNo, String userId) { // 댓글 삭제 합니다.
        ReplyDTO replyDTO = getReply(replyNo);

        if (!replyDTO.getAuthor().equals(userId))
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        replyRepository.delete(replyDTO.toEntity());
    }
}
