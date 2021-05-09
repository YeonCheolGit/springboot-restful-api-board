package com.example.springboot.service.Board;

import com.example.springboot.DTO.CommonParamPost;
import com.example.springboot.advice.exception.UserNotFoundException;
import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.User;
import com.example.springboot.respository.BoardRepository;
import com.example.springboot.respository.PostRepository;
import com.example.springboot.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    // 게시판 이름으로 게시판 조회
    @Override
    public Board findBoard(String boardName) {
        return Optional.ofNullable(boardRepository.findByName(boardName)).orElseThrow(UserNotFoundException::new);
    }

    // 게시판 이름으로 게시물 리스트 조회
    @Override
    public List<Post> findPosts(String boardName) {
        return postRepository.findByBoardNo(findBoard(boardName));
    }

    // 게시물 ID로 단건 조회
    @Override
    public Post getPost(long postNo) {
        return postRepository.findById(postNo).orElseThrow(UserNotFoundException::new);
    }

    // 게시물 등록
    @Override
    public Post writePost(String userId, String boardName, CommonParamPost commonParamPost) {
        Board board = findBoard(boardName);

        Post post = new Post(userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new),
                board, commonParamPost.getAuthor(), commonParamPost.getTitle(), commonParamPost.getContent());
        return postRepository.save(post);
    }

    // 게시물 수정
    @Override
    @Transactional // update dirty checking 위한 설정 입니다
    public Post updatePost(long postNo, String userId, CommonParamPost commonParamPost) {
        Post post = getPost(postNo);
        User user = post.getUserNo();
        if (!userId.equals(user.getUserId())) {
            throw new UserNotFoundException();
        }

        post.setUpdate(commonParamPost.getAuthor(), commonParamPost.getTitle(), commonParamPost.getContent());
        return post;
    }

    // 게시물 삭제
    @Override
    public Boolean deletePost(long postNo, String userId) {
        Post post = getPost(postNo);
        User user = post.getUserNo();

        if (!userId.equals(user.getUserId()))
            throw new UserNotFoundException();

        postRepository.delete(post);
        return true;
    }
}
