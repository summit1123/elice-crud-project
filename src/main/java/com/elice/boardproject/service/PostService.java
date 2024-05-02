package com.elice.boardproject.service;
import com.elice.boardproject.PostNotFoundException;
import com.elice.boardproject.dto.PostDTO;
import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.Post;
import com.elice.boardproject.repository.BoardRepository;
import com.elice.boardproject.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Post getPostById(int postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
    }


    public Post createPost(int boardId, Post post) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid board id: " + boardId));
        post.setBoard(board);
        post.setCreated_at(new Timestamp(System.currentTimeMillis()));
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
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException("Post not found with id: " + postId);
        }
        postRepository.deleteById(postId);
    }

    @Transactional
    public PostDTO getPostDTOById(int postId) {
        Post post = getPostById(postId);
        return new PostDTO(post);
    }

    public Page<Post> searchPosts(Board board, String keyword, Pageable pageable) {
        return postRepository.findByBoardAndTitleContainingOrContentContaining(board, keyword, keyword, pageable);
    }
}