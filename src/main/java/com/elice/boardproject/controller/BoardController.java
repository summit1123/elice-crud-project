package com.elice.boardproject.controller;
import com.elice.boardproject.entity.Board;
import com.elice.boardproject.entity.Post;
import com.elice.boardproject.entity.User;
import com.elice.boardproject.repository.UserRepository;
import com.elice.boardproject.service.BoardService;
import com.elice.boardproject.service.UserService;
import jakarta.transaction.Transactional;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String list(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Board> boardPage = boardService.getAllBoards(pageable);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("boards", boardPage.getContent());
        return "board/boards";
    }

    @GetMapping("/boards/create")
    public String createBoardForm(Principal principal, Model model) {
        if (principal != null) {
            User currentUser = userService.getUserByUsername(principal.getName());
            model.addAttribute("currentUser", currentUser);
            return "board/createBoard";
        } else {
            return "redirect:/login";
        }
    }


    @PostMapping("/boards/create")
    public String createBoard(@ModelAttribute("board") Board board, Principal principal, Model model) {
        try {
            if (principal != null) {
                User currentUser = userService.getUserByUsername(principal.getName());
                board.setUser(currentUser);
                Board createdBoard = boardService.createBoard(board);
                return "redirect:/boards";
            } else {
                return "redirect:/login";
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "존재하지 않는 사용자입니다.");
            model.addAttribute("board", board);
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
        try {
            boardService.updateBoard(boardId, updatedBoard);
            return "redirect:/boards/" + boardId;
        } catch (IllegalArgumentException e) {
            return "error/400";
        }
    }


    @DeleteMapping("/boards/{boardId}")
    public String deleteBoard(@PathVariable int boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/boards";
    }


    @Transactional
    @GetMapping("/boards/{boardId}")
    public String getBoardDetail(@PathVariable int boardId, Model model) {
        Board board = boardService.getBoardById(boardId);
        List<Post> posts = board.getPosts();
        model.addAttribute("board", board);
        model.addAttribute("posts", posts);
        return "board/boardDetail";
    }
}

