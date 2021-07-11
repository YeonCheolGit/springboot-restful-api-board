package com.example.springboot.service;

import com.example.springboot.DTO.board.BoardDTO;
import com.example.springboot.DTO.post.PostDTO;
import com.example.springboot.DTO.reply.ReplyDTO;
import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.Reply;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDTO {

    public BoardDTO toBoardDTO(Board board) {
        return new BoardDTO().builder()
                .boardNo(board.getBoardNo())
                .name(board.getName())
                .build();
    }

    public PostDTO toSinglePostDTO(Post post) {
        return new PostDTO().builder()
                .postNo(post.getPostNo())
                .title(post.getTitle())
                .author(post.getAuthor())
                .userNo(post.getUserNo())
//                .boardNo(post.getBoardNo())
                .replyByPostNo(post.getReplyByPostNo())
                .build();
    }

    public PostDTO toUpdatePostDTO(Post post) {
        return new PostDTO().builder()
                .postNo(post.getPostNo())
                .title(post.getTitle())
                .author(post.getAuthor())
                .userNo(post.getUserNo())
                .boardNo(post.getBoardNo())
                .replyByPostNo(post.getReplyByPostNo())
                .build();
    }

    public ReplyDTO toReplyDTO(Reply reply) {
        return new ReplyDTO().builder()
                .replyNo(reply.getReplyNo())
                .content(reply.getContent())
                .author(reply.getAuthor())
                .postNo(reply.getPostNo())
                .userNo(reply.getUserNo())
                .build();
    }

    public List<BoardDTO> toListBoardDTO(List<Board> boards) {
        return boards.stream().map(Board::toDTO).collect(Collectors.toList());
    }
}
