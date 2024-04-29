package com.elice.boardproject.controller;
import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.repository.UserRepository;
import com.elice.boardproject.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserRepository userService;

    @GetMapping("/boards")
    public String list(Model model) {
        List<Board> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    @GetMapping("/boards/create")
    public String createBoardForm(Model model) {
        model.addAttribute("users", userService.findAll());
        return "board/createBoard";
    }

    @PostMapping("/boards/create")
    public String createBoard(@ModelAttribute Board board, @RequestParam("userId") int userId) {
        User user = userService.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        board.setUser(user);
        boardService.createBoard(board);
        return "redirect:/boards";
    }

    @GetMapping("/boards/{boardId}/edit")
    public String editBoardForm(@PathVariable int boardId, Model model) {
        Board board = boardService.getBoardById(boardId);
        model.addAttribute("board", board);
        return "board/editBoard";
    }


    @PostMapping("/boards/{boardId}/edit")
    public String updateBoard(@PathVariable int boardId, @ModelAttribute Board updatedBoard) {
        boardService.updateBoard(boardId, updatedBoard);
        return "redirect:/boards";
    }

    @PostMapping("/boards/{boardId}/delete")
    public String deleteBoard(@PathVariable int boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/boards";
    }
}

