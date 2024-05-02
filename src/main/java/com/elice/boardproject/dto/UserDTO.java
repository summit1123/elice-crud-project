package com.elice.boardproject.dto;

import com.elice.boardproject.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private int userId;
    private String username;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
    }
}