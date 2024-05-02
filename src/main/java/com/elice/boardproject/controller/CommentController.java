package com.elice.boardproject.controller;


import com.elice.boardproject.entity.Comment;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.service.CommentService;
import com.elice.boardproject.service.UserService;
import jakarta.transaction.Transactional;
import java.security.Principal;
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
    public String createComment(@PathVariable int postId, @ModelAttribute Comment comment) {
        try {
            commentService.createComment(postId, comment);
            return "redirect:/posts/" + postId;
        } catch (IllegalArgumentException e) {
            return "error/404";
        }
    }



    @PostMapping("/comments/{commentId}/edit")
    public String updateComment(@PathVariable int commentId, @ModelAttribute Comment updatedComment) {
        try {
            commentService.updateComment(commentId, updatedComment);
            int postId = commentService.getCommentById(commentId).getPost().getPostId();
            return "redirect:/posts/" + postId;
        } catch (IllegalArgumentException e) {
            return "error/404";
        }
    }


    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(@PathVariable int commentId) {
        try {
            int postId = commentService.getCommentById(commentId).getPost().getPostId();
            commentService.deleteComment(commentId);
            return "redirect:/posts/" + postId;
        } catch (IllegalArgumentException e) {
            return "error/404";
        }
    }


}