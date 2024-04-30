package com.elice.boardproject.controller;


import com.elice.boardproject.entity.Comment;
import com.elice.boardproject.service.CommentService;
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
    public String createComment(@RequestParam("postId") int postId, @ModelAttribute Comment comment) {
        try {
        commentService.createComment(postId, comment);
            return "redirect:/posts/" + postId;
        } catch (IllegalArgumentException e) {
            // 예외 발생 시 에러 페이지로 리다이렉트하거나 에러 메시지를 모델에 담아 View로 전달할 수 있습니다.
            return "error/404";
        }
    }

    @PostMapping("/comments/{commentId}/edit")
    public String updateComment(@PathVariable int commentId, @ModelAttribute Comment updatedComment) {
        try {
            commentService.updateComment(commentId, updatedComment);
            return "redirect:/posts/" + commentService.getCommentById(commentId).getPost()
                .getPost_id();
        } catch (IllegalArgumentException e) {
            return "error/404";
        }
    }


    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(@PathVariable int commentId) {
        try {
            int postId = commentService.getCommentById(commentId).getPost().getPost_id();
            commentService.deleteComment(commentId);
            return "redirect:/posts/" + postId;
        } catch (IllegalArgumentException e) {
            return "error/404";
        }
    }


}