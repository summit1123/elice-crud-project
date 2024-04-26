package com.elice.boardproject.controller;

import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.Post;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.repository.BoardRepository;
import com.elice.boardproject.repository.PostRepository;
import com.elice.boardproject.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Date;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @GetMapping("/boards")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    @GetMapping("/boards/create")
    public String createForm() {
        return "board/createBoard";
    }

    @PostMapping("/boards/create")
    public String create(@RequestParam("name") String name, @RequestParam("description") String description) {

        // user 1만 가져오깅.
        User user = userRepository.findById(1).orElseThrow();

        Date date = new Date(System.currentTimeMillis());
        Board board = new Board();
        board.setBoard_name(name);
        board.setDescription(description);
        board.setCreated_at(date);
        board.setUser(user);
        boardRepository.save(board);
        return "redirect:/boards";
    }

    @GetMapping("/boards/{boardId}")
    public String detail(@PathVariable Integer boardId, @RequestParam(required = false) String keyword,
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Invalid board id: " + boardId));
        Page<Post> postPage;
        if (keyword != null) {
            postPage = postRepository.findByBoardAndTitleContainingOrContentContaining(board, keyword, keyword, pageable);
        } else {
            postPage = postRepository.findByBoard(board, pageable);
        }
        model.addAttribute("board", board);
        model.addAttribute("postPage", postPage);
        model.addAttribute("keyword", keyword);
        return "board/board";
    }

    @GetMapping("/boards/{boardId}/edit")
    public String editForm(@PathVariable Integer boardId, Model model) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Invalid board id: " + boardId));
        model.addAttribute("board", board);
        return "board/editBoard";
    }

    @PostMapping("/boards/{boardId}/edit")
    public String edit(@PathVariable Integer boardId, @RequestParam String name, @RequestParam String description) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Invalid board id: " + boardId));
        board.setBoard_name(name);
        board.setDescription(description);
        boardRepository.save(board);
        return "redirect:/boards";
    }

    @DeleteMapping("/boards/{boardId}/delete")
    public String delete(@PathVariable Integer boardId) {
        boardRepository.deleteById(boardId);
        return "redirect:/boards";
    }
}