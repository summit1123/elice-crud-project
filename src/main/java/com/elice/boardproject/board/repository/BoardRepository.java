package com.elice.boardproject.board.repository;

import com.elice.boardproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BoardRepository extends JpaRepository<Board, Long> {

}

