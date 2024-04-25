package com.elice.boardproject.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
public class Board {
    @Id
    private int id;
    private String name;
    private String description;
    //db setting clear
}
