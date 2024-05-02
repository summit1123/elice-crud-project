package com.elice.boardproject.controller;
import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.Post;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.repository.UserRepository;
import com.elice.boardproject.service.BoardService;
import com.elice.boardproject.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    @GetMapping("/boards")
    public String list(Model model) {
        List<Board> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    @GetMapping("/boards/create")
    public String createBoardForm(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "board/createBoard";
    }

    @PostMapping("/boards/create")
    public String createBoard(@ModelAttribute("board") Board board, @RequestParam("userId") int userId, Model model) {
        try {
            User user = userService.getUserById(userId);
            board.setUser(user);
            boardService.createBoard(board);
            return "redirect:/boards";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "존재하지 않는 사용자입니다.");
            model.addAttribute("board", board);
            model.addAttribute("users", userService.getAllUsers());
            return "board/createBoard";
        }
    }

    @GetMapping("/boards/{boardId}/edit")
    public String editBoardForm(@PathVariable int boardId, Model model) {
        try {
            Board board = boardService.getBoardById(boardId);
            model.addAttribute("board", board);
            return "board/editBoard";
        } catch (IllegalArgumentException e) {
            return "error/404";
        }
    }


    @PostMapping("/boards/{boardId}/edit")
    public String updateBoard(@PathVariable int boardId, @ModelAttribute Board updatedBoard) {
        boardService.updateBoard(boardId, updatedBoard);
        return "redirect:/boards";
    }


    @DeleteMapping("/boards/{boardId}/delete")
    public void deleteBoard(@PathVariable int boardId) {
        boardService.deleteBoard(boardId);
    }


    @GetMapping("/boards/{boardId}")
    public String getBoardDetail(@PathVariable int boardId, Model model) {
        Board board = boardService.getBoardById(boardId);
        List<Post> emptyBoardList = Collections.emptyList(); // 빈 리스트 생성
        board.setPosts(emptyBoardList);
        model.addAttribute("board", board);
        return "board/boardDetail";
    }
}

