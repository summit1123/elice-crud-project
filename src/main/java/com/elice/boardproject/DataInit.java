package com.elice.boardproject;


import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.service.BoardService;
import com.elice.boardproject.service.CommentService;
import com.elice.boardproject.service.PostService;
import com.elice.boardproject.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final BoardService boardService;
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

//    @PostConstruct
//    @Transactional
//    public void initData() {
//    }

}
