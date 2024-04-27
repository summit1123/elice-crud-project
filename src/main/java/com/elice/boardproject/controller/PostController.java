package com.elice.boardproject.controller;


import ch.qos.logback.core.model.Model;
import com.elice.boardproject.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
    @RequiredArgsConstructor
    public class PostController {
        private final PostService postService;
        private final BoardService boardService;

    @GetMapping("/boards/{boardId}/posts/create")
    public String createPostForm(@PathVariable Long boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "post/createPost";
    }

    @PostMapping("/boards/{boardId}/posts")
    public String createPost(@PathVariable Long boardId, @ModelAttribute Post post) {
        postService.createPost(boardId, post);
        return "redirect:/boards/" + boardId;
    }

    @GetMapping("/posts/{postId}")
    public String getPost(@PathVariable Long postId, Model model) {
        model.addAttribute("post", postService.getPostById(postId));
        return "post/post";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPostForm(@PathVariable Long postId, Model model) {
        model.addAttribute("post", postService.getPostById(postId));
        return "post/editPost";
    }

    @PutMapping("/posts/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute Post post) {
        postService.updatePost(postId, post);
        return "redirect:/posts/" + postId;
    }

    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable Long postId) {
        Long boardId = postService.getPostById(postId).getBoard().getId();
        postService.deletePost(postId);
        return "redirect:/boards/" + boardId;
    }
}


