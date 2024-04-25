package com.elice.boardproject.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@AllArgsConstructor
@Data
public class Post {
    @Id
    private int id;
    private String title;
    private String content;

}
