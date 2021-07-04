package com.example.springboot.service.board;

import com.example.springboot.DTO.CommonParamPost;
import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;

import java.util.List;

public interface BoardService {

    Board findBoard(String boardName);

    List<Post> findPosts(String boardName);

    Post getPost(long postId);

    Post writePost(String userId, String boardName, CommonParamPost commonParamPost);

    Post updatePost(long postNo, String userId, CommonParamPost commonParamPost);

    Boolean deletePost(long postNo, String userId);
}
