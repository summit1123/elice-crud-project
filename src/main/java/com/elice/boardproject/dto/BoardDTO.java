package com.elice.boardproject.dto;

import com.elice.boardproject.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {
    private int boardId;
    private String boardName;

    public BoardDTO(Board board) {
        this.boardId = board.getBoardId();
        this.boardName = board.getBoardName();
    }
}
