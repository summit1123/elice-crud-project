package com.elice.boardproject.service;
import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.Post;
import com.elice.boardproject.repository.BoardRepository;
import com.elice.boardproject.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    public Post getPostById(int postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid post id: " + postId));
    }

    public Page<Post> getPostsByBoard(Board board, Pageable pageable) {
        return postRepository.findByBoard(board, pageable);
    }

    @Transactional
    public Post createPost(int boardId, Post post) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid board id: " + boardId));
        post.setBoard(board);
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(int postId, Post updatedPost) {
        Post post = getPostById(postId);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        return postRepository.save(post);
    }

    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }

    public Page<Post> searchPosts(Board board, String keyword, Pageable pageable) {
        return postRepository.findByBoardAndTitleContainingOrContentContaining(board, keyword, keyword, pageable);
    }
}