package com.example.springboot.service.Board;

import com.example.springboot.DTO.CommonParamPost;
import com.example.springboot.advice.exception.FindAnyFailException;
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

    /*
     * 게시판 이름으로 특정 게시판 글 조회 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional
    public Board findBoard(String boardName) {
        return Optional.ofNullable(boardRepository.findByName(boardName)).orElseThrow(FindAnyFailException::new);
    }

    /*
     * 게시물 번호로 특정 게시물 찾습니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional
    public List<Post> findPosts(String boardName) {
        return postRepository.findByBoardNo(findBoard(boardName)).orElseThrow(FindAnyFailException::new); // 없는 게시물 번호 조회 시 발생 합니다.
    }

    /*
     * 한 개의 특정 게시물 조회 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional
    public Post getPost(long postNo) {
        return postRepository.findById(postNo).orElseThrow(FindAnyFailException::new); // 없는 게시물 조회 시 발생 합니다.
    }

    /*
     * 게시물 등록 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional
    public Post writePost(String userId, String boardName, CommonParamPost commonParamPost) {
        Board board = findBoard(boardName);

        Post post = new Post(userRepository.findByUserId(userId).orElseThrow(FindAnyFailException::new), // 회원이 아닌 경우 등록할 수 없습니다.
                board, commonParamPost.getAuthor(), commonParamPost.getTitle(), commonParamPost.getContent());

        return postRepository.save(post);
    }

    /*
     * 게시물 수정 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional // update dirty checking 위한 설정 입니다
    public Post updatePost(long postNo, String userId, CommonParamPost commonParamPost) {
        Post post = getPost(postNo);
        User user = post.getUserNo();

        if (!userId.equals(user.getUserId())) { // 게시글 작성자와 현재 로그인 회원이 다를 경우 수정할 수 없습니다.
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");
        }

        post.setUpdate(commonParamPost.getAuthor(), commonParamPost.getTitle(), commonParamPost.getContent());
        return post;
    }

    /*
     * 게시물 삭제 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional
    public Boolean deletePost(long postNo, String userId) {
        Post post = getPost(postNo);
        User user = post.getUserNo();

        if (!userId.equals(user.getUserId())) // 게시글 작성자와 현재 로그인 회원이 다를 경우 삭제할 수 없습니다.
            throw new FindAnyFailException("타인의 글은 삭제할 수 없습니다.");

        postRepository.delete(post);
        return true;
    }
}
