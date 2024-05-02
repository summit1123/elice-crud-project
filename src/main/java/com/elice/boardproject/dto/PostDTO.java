package com.elice.boardproject.dto;

import com.elice.boardproject.entity.Post;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private int postId;
    private String title;
    private String content;
    private String username;
    private Timestamp created_at;
    private BoardDTO board;
    private UserDTO user;
    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUsername();
        this.created_at = post.getCreated_at();
        this.board = new BoardDTO(post.getBoard()); // post.getBoard()의 반환 타입이 Board이므로 BoardDTO 생성자에 Board 객체를 전달
        this.user = new UserDTO(post.getUser());

    }
}

