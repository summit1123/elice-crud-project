package com.elice.boardproject.repository;

import com.elice.boardproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
