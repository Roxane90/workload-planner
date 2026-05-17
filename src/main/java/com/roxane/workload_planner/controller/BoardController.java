package com.roxane.workload_planner.controller;

import com.roxane.workload_planner.model.Board;
import com.roxane.workload_planner.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public String getAllBoards(Model model) {
        model.addAttribute("boards", boardService.getAllBoards());
        return "boards/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("board", new Board());
        return "boards/form";
    }

    @PostMapping
    public String createBoard(@ModelAttribute Board board,
                              RedirectAttributes redirectAttributes) {
        boardService.createBoard(board);
        redirectAttributes.addFlashAttribute("successMessage", "Board created successfully!");
        return "redirect:/boards";
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@PathVariable Long id,
                              RedirectAttributes redirectAttributes) {
        boardService.deleteBoard(id);
        redirectAttributes.addFlashAttribute("successMessage", "Board deleted successfully!");
        return "redirect:/boards";
    }
}