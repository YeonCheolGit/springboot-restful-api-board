package com.example.springboot.service.board;

import com.example.springboot.DTO.CommonParamPost;
import com.example.springboot.DTO.board.BoardDTO;
import com.example.springboot.DTO.post.PostDTO;
import com.example.springboot.advice.exception.FindAnyFailException;
import com.example.springboot.entity.Board;
import com.example.springboot.entity.Post;
import com.example.springboot.entity.User;
import com.example.springboot.respository.BoardRepository;
import com.example.springboot.respository.PostRepository;
import com.example.springboot.respository.UserRepository;
import com.example.springboot.service.ToDTO;
import com.example.springboot.service.ToEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final ToDTO toDTO;
    private final ToEntity toEntity;

    /*
     * 게시판 이름으로 특정 게시판 정보를 조회 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional
    public BoardDTO findBoardDTO(String boardName) {
        Board board = boardRepository.findByName(boardName).orElseThrow(FindAnyFailException::new);
        return toDTO.toBoardDTO(board);
    }

    /*
     * 게시판 이름으로 특정 게시판 글 조회 합니다.
     * findByBoardNo(findBoard(boardName)) - 게시판 이름으로 찾은 후 해당 No를 기준으로 게시물 찾습니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
//    @Cacheable(key = "#boardName", value = "findPosts")
    @Override
    @Transactional
    public List<BoardDTO> findPosts(String boardName) {
        return toDTO.toListBoardDTO(boardRepository.findBoardByBoardNo(findBoardDTO(boardName).getBoardNo()).orElseThrow(FindAnyFailException::new)); // 없는 게시물 번호 조회 시 발생 합니다.
//        return postRepository.findByBoardNo(findBoardDTO(boardName)).orElseThrow(FindAnyFailException::new); // 없는 게시물 번호 조회 시 발생 합니다.
    }

    /*
     * 한 개의 특정 게시물 조회 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional
    public PostDTO getPost(long postNo) {
        Post post = postRepository.findByPostNo(postNo).orElseThrow(FindAnyFailException::new);
        return toDTO.toSinglePostDTO(post); // Entity to DTO
    }


    /*
     * 게시물 등록 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional
    public void writePost(String userId, String boardName, CommonParamPost commonParamPost) {
        BoardDTO board = findBoardDTO(boardName);

        PostDTO postDTO = new PostDTO().builder()
                .userNo(userRepository.findByUserId(userId).orElseThrow(FindAnyFailException::new))
                .boardNo(toEntity.toBoardEntity(board))
                .author(commonParamPost.getAuthor())
                .title(commonParamPost.getTitle())
                .content(commonParamPost.getContent())
                .build();

        postRepository.save(toEntity.toPostEntity(postDTO));
    }

    /*
     * 게시물 수정 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional // update dirty checking 위한 설정 입니다
    public void updatePost(long postNo, String userId, CommonParamPost commonParamPost) {
        PostDTO postDTO = toDTO.toUpdatePostDTO(postRepository.findByPostNo(postNo).orElseThrow(FindAnyFailException::new));
//        PostDTO postDTO = getPost(postNo);
        User user = postDTO.getUserNo();

        if (!userId.equals(user.getUserId())) // 게시글 작성자와 현재 로그인 회원이 다를 경우 수정할 수 없습니다.
            throw new FindAnyFailException("타인의 글은 수정할 수 없습니다.");

        postDTO.setUpdate(commonParamPost.getAuthor(), commonParamPost.getTitle(), commonParamPost.getContent());
        postRepository.save(toEntity.toPostEntity(postDTO));
    }

    /*
     * 게시물 삭제 합니다.
     * FindAnyFailException - 없는 데이터 조회 경우 발생합니다.
     */
    @Override
    @Transactional
    public Boolean deletePost(long postNo, String userId) {
        PostDTO post = getPost(postNo);
        User user = post.getUserNo();

        if (!userId.equals(user.getUserId())) // 게시글 작성자와 현재 로그인 회원이 다를 경우 삭제할 수 없습니다.
            throw new FindAnyFailException("타인의 글은 삭제할 수 없습니다.");

        postRepository.delete(toEntity.toPostEntity(post));
        return true;
    }
}
