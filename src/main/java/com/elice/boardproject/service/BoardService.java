package com.elice.boardproject.service;

import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.repository.BoardRepository;
import com.elice.boardproject.repository.PostRepository;
import com.elice.boardproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<Board> getAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional
    public Board getBoardById(int boardId) {
        return boardRepository.findByIdWithPostsAndUsers(boardId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid board id: " + boardId));
    }

    @Transactional
    public Board createBoard(Board board) {
        board.setCreated_at(new Timestamp(System.currentTimeMillis()));
        return boardRepository.save(board);
    }

    public Board updateBoard(int boardId, Board updatedBoard) {
        Board board = getBoardById(boardId);
        board.setBoardName(updatedBoard.getBoardName());
        board.setDescription(updatedBoard.getDescription());
        return boardRepository.save(board);
    }

    public void deleteBoard(int boardId) {
        boardRepository.deleteById(boardId);
    }
}