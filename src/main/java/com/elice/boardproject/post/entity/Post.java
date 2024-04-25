package com.elice.boardproject.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@AllArgsConstructor
@Data
public class Post {
    @Id
    private Long id;
    private String title;

    @Column(length = 10000)
    private String content;

}
