package com.elice.boardproject.repository;

import com.elice.boardproject.entity.Board;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.posts WHERE b.board_id = :boardId")
    Optional<Board> findByIdWithPosts(@Param("boardId") int boardId);

}