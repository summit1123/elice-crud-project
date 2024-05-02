package com.elice.boardproject.repository;

import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByBoardAndTitleContainingOrContentContaining(Board board, String title, String content, Pageable pageable);

    Page<Post> findByBoard(Board board, Pageable pageable);
}