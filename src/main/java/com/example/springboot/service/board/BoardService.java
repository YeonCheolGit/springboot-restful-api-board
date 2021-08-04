package com.example.springboot.service.board;

import com.example.springboot.DTO.CommonParamPost;
import com.example.springboot.DTO.board.OnlyBoardDTO;
import com.example.springboot.DTO.post.ListPostDTO;
import com.example.springboot.DTO.post.PostDTO;
import com.example.springboot.DTO.post.SinglePostDTO;
import com.example.springboot.advice.exception.FindAnyFailException;
import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.User;
import com.example.springboot.respository.BoardRepository;
import com.example.springboot.respository.PostRepository;
import com.example.springboot.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final CacheManager cacheManager;

    /*
     * 게시판 이름으로 특정 게시판 정보를 조회 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Transactional
    public OnlyBoardDTO findBoardDTO(String boardName) {
        Board board = boardRepository.findByName(boardName).orElseThrow(FindAnyFailException::new);
        return new OnlyBoardDTO().toOnlyBoardDTO(board);
    }

    /*
     * 게시판 이름으로 특정 게시판 글 조회 합니다.
     * board - 게시판 이름으로 게시판의 정보 가져옵니다.
     * posts - 찾은 게시판(board)에 해당하는 게시물 가져옵니다.
     */
    @Cacheable(cacheNames = "findPost", value = "findPost", key = "#boardName")
    @Transactional
    public List<ListPostDTO> findPosts(String boardName) {
        Board board = boardRepository.findByName(boardName).orElseThrow(FindAnyFailException::new);
        List<Post> posts = postRepository.findPostByBoard(board).orElseThrow(FindAnyFailException::new);
        return new ListPostDTO().toListPostDTO(posts);
    }

    /*
     * 한 개의 특정 게시물 조회 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Transactional
    public SinglePostDTO getPost(long postNo) {
        Post post = postRepository.findByPostNo(postNo).orElseThrow(FindAnyFailException::new);
        return new SinglePostDTO().requestSinglePostDTO(post);
    }

    /*
     * 게시물 등록 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @CachePut(cacheNames = "writePost", value = "writePost", key = "#boardName")
    @Transactional
    public void writePost(String userId, String boardName, CommonParamPost commonParamPost) {
        OnlyBoardDTO boardDTO = findBoardDTO(boardName);

        PostDTO postDTO = PostDTO.builder()
                .user(userRepository.findByUserId(userId).orElseThrow(FindAnyFailException::new))
                .board(boardDTO.toBoardEntity(boardDTO))
                .author(commonParamPost.getAuthor())
                .title(commonParamPost.getTitle())
                .content(commonParamPost.getContent())
                .build();

        postRepository.save(postDTO.toPostEntity(postDTO));

        Objects.requireNonNull(cacheManager.getCache("findPost")).clear(); // 새 글 작성 후 전체글 목록 캐시 삭제
    }

    /*
     * 게시물 수정 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @CachePut(cacheNames = "updatePost", value = "updatePost", key = "#postNo")
    @Transactional
    public void updatePost(long postNo, String userId, CommonParamPost commonParamPost) {
        PostDTO postDTO = new PostDTO()
                .toPostDTO(postRepository.findByPostNo(postNo).orElseThrow(FindAnyFailException::new));
        User user = postDTO.getUser();

        if (!userId.equals(user.getUserId())) // 게시글 작성자와 현재 로그인 회원이 다를 경우 수정할 수 없습니다.
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        postDTO.setUpdate(commonParamPost.getAuthor(), commonParamPost.getTitle(), commonParamPost.getContent());
        postRepository.save(postDTO.toPostEntity(postDTO));

        Objects.requireNonNull(cacheManager.getCache("findPost")).clear(); // 글 수정 후 전체글 목록 캐시 삭제
    }

    /*
     * 게시물 삭제 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @CacheEvict(cacheNames = "deletePost", value = "deletePost", key = "#postNo")
    @Transactional
    public Boolean deletePost(long postNo, String userId) {
        SinglePostDTO singlePostDTO = getPost(postNo);
        User user = singlePostDTO.getUser();

        if (!userId.equals(user.getUserId())) // 게시글 작성자와 현재 로그인 회원이 다를 경우 삭제할 수 없습니다.
            throw new FindAnyFailException("타인의 글은 삭제할 수 없습니다.");

        postRepository.delete(singlePostDTO.toPostEntity(singlePostDTO));

        Objects.requireNonNull(cacheManager.getCache("findPost")).clear(); // 글 삭제 후 새 전체글 목록 캐시 삭제

        return true;
    }
}
