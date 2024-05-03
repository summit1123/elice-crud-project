package com.elice.boardproject.controller;


import com.elice.boardproject.entity.Comment;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.service.CommentService;
import com.elice.boardproject.service.UserService;
import jakarta.transaction.Transactional;
import java.security.Principal;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/posts/{postId}/comments")
    public String createComment(@PathVariable int postId, @ModelAttribute Comment comment, Principal principal) {
        try {
            if (principal != null) {
                User currentUser = userService.getUserByUsername(principal.getName());
                comment.setUser(currentUser);
                commentService.createComment(postId, comment);
                comment.setCreated_at(new Timestamp(System.currentTimeMillis()));
                return "redirect:/posts/" + postId;
            } else {
                // 인증되지 않은 사용자에 대한 처리 로직 추가
                return "redirect:/login";
            }
        } catch (IllegalArgumentException e) {
            return "error/404";
        }
    }



    @Transactional
    @PostMapping("/comments/{commentId}/edit")
    public String updateComment(@PathVariable int commentId, @ModelAttribute("updatedComment") Comment updatedComment, Principal principal) {
        try {
            Comment comment = commentService.getCommentById(commentId);
            if (principal != null && principal.getName().equals(comment.getUser().getUsername())) {
                updatedComment.setUser(comment.getUser()); // 기존 댓글 작성자 정보 유지
                updatedComment.setPost(comment.getPost());
                updatedComment.setCreated_at(comment.getCreated_at());
                commentService.updateComment(commentId, updatedComment);
                return "redirect:/posts/" + comment.getPost().getPostId();
            } else {
                // 인증되지 않은 사용자 또는 댓글 작성자가 아닌 경우 처리
                return "error/403";
            }
        } catch (IllegalArgumentException e) {
            return "error/404";
        }
    }





    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(@PathVariable int commentId) {
        int postId = commentService.getCommentById(commentId).getPost().getPostId();
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }


}