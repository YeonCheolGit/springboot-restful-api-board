package com.example.springboot.service;

import com.example.springboot.DTO.board.BoardDTO;
import com.example.springboot.DTO.post.PostDTO;
import com.example.springboot.DTO.reply.ReplyDTO;
import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import org.springframework.stereotype.Service;

@Service
public class ToEntity {

    public Board toBoardEntity(BoardDTO boardDTO) {
        return Board.builder()
                .boardNo(boardDTO.getBoardNo())
                .name(boardDTO.getName())
                .build();
    }

    public Post toPostEntity(PostDTO postDTO) {
        return Post.builder()
                .postNo(postDTO.getPostNo())
                .title(postDTO.getTitle())
                .author(postDTO.getAuthor())
                .content(postDTO.getContent())
                .userNo(postDTO.getUserNo())
                .boardNo(postDTO.getBoardNo())
                .replyByPostNo(postDTO.getReplyByPostNo())
                .build();
    }

    public Reply toReplyEntity(ReplyDTO replyDTO) {
        return Reply.builder()
                .replyNo(replyDTO.getReplyNo())
                .content(replyDTO.getContent())
                .author(replyDTO.getAuthor())
                .postNo(replyDTO.getPostNo())
                .userNo(replyDTO.getUserNo())
                .build();
    }
}


