package com.elice.boardproject.controller;

//깃 .DS_Store 파일 삭제
import com.elice.boardproject.entity.Comment;
import com.elice.boardproject.entity.Post;
import com.elice.boardproject.service.CommentService;
import com.elice.boardproject.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/posts/{postId}")
    public String getPost(@PathVariable int postId, Model model) {
        Post post = postService.getPostById(postId);
        model.addAttribute("post", post);
        List<Comment> comments = commentService.getCommentsByPost(post);
        model.addAttribute("comments", comments);
        return "post/post";
    }

    @GetMapping("/posts/create")
    public String createPostForm(@RequestParam("boardId") int boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "post/createPost";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam("boardId") int boardId, @ModelAttribute Post post) {
        postService.createPost(boardId, post);
        return "redirect:/boards/" + boardId;
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
        postService.deletePost(postId);
        return "redirect:/boards/" + postService.getPostById(postId).getBoard().getBoard_id();
    }
}