package com.elice.boardproject.controller;


import com.elice.boardproject.PostNotFoundException;
import com.elice.boardproject.dto.CommentDTO;
import com.elice.boardproject.dto.PostDTO;
import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.Comment;
import com.elice.boardproject.entity.Post;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.service.CommentService;
import com.elice.boardproject.service.PostService;
import com.elice.boardproject.service.UserService;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("/posts/{postId}")
    public String getPost(@PathVariable int postId, Model model) {
        try {
            PostDTO postDTO = postService.getPostDTOById(postId);
            model.addAttribute("post", postDTO);

            List<CommentDTO> commentDTOs = commentService.getCommentDTOsByPost(
                postService.getPostById(postId));
            model.addAttribute("comments", commentDTOs);

            return "post/post";
        } catch (PostNotFoundException e) {
            return "error/404";
        } catch (Exception e) {
            return "error/500";
        }
    }


    @GetMapping("/posts/create")
    public String createPostForm(@RequestParam("boardId") int boardId, Principal principal,
        Model model) {
        if (principal != null) {
            User currentUser = userService.getUserByUsername(principal.getName());
            model.addAttribute("boardId", boardId);
            model.addAttribute("currentUser", currentUser);
        } else {
            // 인증되지 않은 사용자에 대한 처리 로직 추가
            return "redirect:/login";
        }
        return "post/createPost";
    }


    @PostMapping("/posts/create")
    public String createPost(@RequestParam int boardId, @ModelAttribute Post post,
        Principal principal, Model model) {
        try {
            if (principal != null) {
                User currentUser = userService.getUserByUsername(principal.getName());
                post.setUser(currentUser);
                postService.createPost(boardId, post);
                return "redirect:/boards/" + boardId;
            } else {
                return "redirect:/login";
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("boardId", boardId);
            return "post/createPost";
        }
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPostForm(@PathVariable int postId, Model model) {
        Post post = postService.getPostById(postId);
        model.addAttribute("post", post);
        return "post/editPost";
    }

    @PostMapping("/posts/{postId}/edit")
    public String updatePost(@PathVariable int postId, @ModelAttribute Post post) {
        postService.updatePost(postId, post);
        return "redirect:/posts/" + postId;
    }

    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable int postId) {
        int boardId = postService.getPostById(postId).getBoard().getBoardId();
        postService.deletePost(postId);
        return "redirect:/boards/" + boardId;
    }
}
