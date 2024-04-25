package com.elice.boardproject.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Comment {
    @Id
    private Long id;
    private String content;
    private String name;


}
