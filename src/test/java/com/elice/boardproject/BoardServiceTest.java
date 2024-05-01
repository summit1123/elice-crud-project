package com.elice.boardproject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.elice.boardproject.entity.Board;
import com.elice.boardproject.repository.BoardRepository;
import com.elice.boardproject.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// BoardServiceTest.java
@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    void createBoard_ValidInput_ReturnsSavedBoard() {
        // given
        Board board = new Board();
        board.setBoard_name("Test Board");
        board.setDescription("This is a test board");

        when(boardRepository.save(any(Board.class))).thenReturn(board);

        // when
        Board savedBoard = boardService.createBoard(board);

        // then
        assertThat(savedBoard.getBoard_name()).isEqualTo("Test Board");
        assertThat(savedBoard.getDescription()).isEqualTo("This is a test board");
        verify(boardRepository, times(1)).save(board);
    }

    // ... 다른 테스트 메소드 작성
}


