package com.elice.boardproject.board;

import com.elice.boardproject.board.entity.Board;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
    @RequestMapping(value = "/")
    public ModelAndView first() {
        List<Board> boards = new ArrayList<>();
        Board board = new Board(1,"김동현", "love you");
        boards.add(board);
        Board board1 = new Board(2,"장민성", "hate you");
        boards.add(board1);
        ModelAndView Mav = new ModelAndView("board/boards");
        Mav.addObject("boards", boards);
        return Mav;

    }ddddddd

    @RequestMapping(value = "/boards/create")
    public ModelAndView second() {
        ModelAndView Mav = new ModelAndView("board/createBoard");
        return Mav;
    }
}
