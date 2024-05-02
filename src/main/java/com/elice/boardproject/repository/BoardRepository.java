package com.elice.boardproject.repository;

import com.elice.boardproject.entity.Board;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT DISTINCT b FROM Board b " +
        "LEFT JOIN FETCH b.posts p " +
        "LEFT JOIN FETCH p.user " +
        "WHERE b.boardId = :boardId")
    Optional<Board> findByIdWithPostsAndUsers(@Param("boardId") Integer boardId);
}