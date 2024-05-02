package com.elice.boardproject.dto;

import com.elice.boardproject.entity.Comment;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private int commentId;
    private String content;
    private String username;
    private Timestamp created_at;

    public CommentDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.created_at = comment.getCreated_at();
    }
}