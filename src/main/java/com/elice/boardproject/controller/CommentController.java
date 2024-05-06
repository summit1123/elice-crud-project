package com.elice.boardproject.controller;


import com.elice.boardproject.entity.Comment;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.service.CommentService;
import com.elice.boardproject.service.UserService;
import jakarta.transaction.Transactional;
import java.security.Principal;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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



    @PostMapping("/comments/{commentId}/edit")
    public String updateComment(@PathVariable int commentId, @ModelAttribute("updatedComment") Comment updatedComment, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            Comment comment = commentService.getCommentById(commentId);
            String username = principal.getName();
            com.elice.boardproject.entity.User user = userService.getUserByUsername(username);

            if (user != null && comment.getUser().getUserId() == user.getUserId()) {
                updatedComment.setUser(comment.getUser());
                updatedComment.setPost(comment.getPost());
                updatedComment.setCreated_at(comment.getCreated_at());
                commentService.updateComment(commentId, updatedComment);
                return "redirect:/posts/" + comment.getPost().getPostId();
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "댓글을 수정할 권한이 없습니다.");
                return "redirect:/posts/" + comment.getPost().getPostId();
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "잘못된 요청입니다.");
            return "redirect:/posts/" + commentService.getCommentById(commentId).getPost().getPostId();
        }
    }

    @GetMapping("/comments/{commentId}/edit")
    public String editCommentForm(@PathVariable int commentId, Model model, Principal principal) {
        Comment comment = commentService.getCommentById(commentId);
        model.addAttribute("comment", comment);

        if (principal != null) {
            String username = principal.getName();
            com.elice.boardproject.entity.User user = userService.getUserByUsername(username);
            model.addAttribute("currentUser", user);
        }

        return "post/editComment";
    }

    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(@PathVariable int commentId) {
        int postId = commentService.getCommentById(commentId).getPost().getPostId();
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }


}