package com.elice.boardproject.controller;

import com.elice.boardproject.entity.Board;
import com.elice.boardproject.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


//    @RequestMapping(value = "/")
//    public ModelAndView first() {
//       List<Board> boards = new ArrayList<>();
//        Board board = new Board(1L,"김동현", "love you");
//        boards.add(board);
//        Board board1 = new Board(2L,"장민성", "hate you");
//        boards.add(board1);
//        ModelAndView Mav = new ModelAndView("board/boards");
//        Mav.addObject("boards", boards);
//        return Mav;

    @Controller
    @RequiredArgsConstructor
    public class BoardController {
    private final BoardRepository boardRepository;


        @GetMapping("/")
        String list(Model model) {
            List<Board> result = boardRepository.findAll(); // 모든 게시판 목록을 조회합니다.
            System.out.println(result);
            model.addAttribute("boards", result); // 조회한 게시판 목록을 모델에 추가합니다.
            return "board/boards"; // 반환할 뷰 템플릿의 이름을 수정합니다.

    }


    @PostMapping("/boards")
    String addPost(@RequestParam String board_name, @RequestParam String description) {
        Board board = new Board();
        board.setBoard_name(board_name);
        board.setDescription(description);
        boardRepository.save(board);
        return "redirect:/";
    }
}







