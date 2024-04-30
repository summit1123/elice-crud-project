package com.elice.boardproject.service;


import com.elice.boardproject.entity.Comment;
import com.elice.boardproject.entity.Post;
import com.elice.boardproject.repository.CommentRepository;
import com.elice.boardproject.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment getCommentById(int commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid comment id: " + commentId));
    }

    public Comment createComment(int postId, Comment comment) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid post id: " + postId));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public Comment updateComment(int commentId, Comment updatedComment) {
        Comment comment = getCommentById(commentId);
        comment.setContent(updatedComment.getContent());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }
}