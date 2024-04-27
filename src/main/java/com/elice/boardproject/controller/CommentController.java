package com.elice.boardproject.controller;


import com.elice.boardproject.entity.Comment;
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

        @PostMapping("/comments")
        public String createComment(@RequestParam("postId") Long postId, @ModelAttribute Comment comment) {
            commentService.createComment(postId, comment);
            return "redirect:/posts/" + postId;
        }

        @PostMapping("/comments/{commentId}/edit")
        public String updateComment(@PathVariable Long commentId, @ModelAttribute Comment comment) {
            commentService.updateComment(commentId, comment);
            return "redirect:/posts/" + commentService.getCommentById(commentId).getPost().getId();
        }

        @DeleteMapping("/comments/{commentId}")
        public String deleteComment(@PathVariable Long commentId) {
            Long postId = commentService.getCommentById(commentId).getPost().getId();
            commentService.deleteComment(commentId);
            return "redirect:/posts/" + postId;
        }
    }


