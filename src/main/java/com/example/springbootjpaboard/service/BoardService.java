package com.example.springbootjpaboard.service;

import com.example.springbootjpaboard.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    List<Board> findAll();

    Optional<Board> findById(Long id);

    Board save(Board board);

    void deleteById(Long id);
}
