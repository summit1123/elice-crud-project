package com.elice.boardproject.service;

import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.repository.BoardRepository;
import com.elice.boardproject.repository.PostRepository;
import com.elice.boardproject.repository.UserRepository;
import java.sql.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardById(int boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid board id: " + boardId));
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Board updateBoard(int boardId, Board updatedBoard) {
        Board board = getBoardById(boardId);
        board.setBoard_name(updatedBoard.getBoard_name());
        board.setDescription(updatedBoard.getDescription());
        return boardRepository.save(board);
    }

    public void deleteBoard(int boardId) {
        boardRepository.deleteById(boardId);
    }
}