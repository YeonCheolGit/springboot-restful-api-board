package com.example.springboot.service.board;

import com.example.springboot.DTO.CommonParamPost;
import com.example.springboot.DTO.board.BoardDTO;
import com.example.springboot.DTO.post.PostDTO;
import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;

import java.util.List;

public interface BoardService {
    BoardDTO findBoardDTO(String boardName);

    List<BoardDTO> findPosts(String boardName);

    PostDTO getPost(long postId);

    void writePost(String userId, String boardName, CommonParamPost commonParamPost);

    void updatePost(long postNo, String userId, CommonParamPost commonParamPost);

    Boolean deletePost(long postNo, String userId);
}
