package com.elice.boardproject.repository;

import com.elice.boardproject.entity.Comment;
import com.elice.boardproject.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
}