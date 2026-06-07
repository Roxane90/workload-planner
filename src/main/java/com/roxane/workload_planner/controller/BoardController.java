package com.roxane.workload_planner.controller;

import com.roxane.workload_planner.model.Board;
import com.roxane.workload_planner.repository.UserRepository;
import com.roxane.workload_planner.service.BoardService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/boards")
public class BoardController extends BaseController {

    private final BoardService boardService;

    public BoardController(BoardService boardService,
                           UserRepository userRepository) {
        super(userRepository);
        this.boardService = boardService;
    }

    @GetMapping
    public String getAllBoards(Model model, Authentication authentication) {
        model.addAttribute("boards", boardService.getAllBoards());
        model.addAttribute("displayName", getDisplayName(authentication));
        return "boards/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model, Authentication authentication) {
        model.addAttribute("board", new Board());
        model.addAttribute("displayName", getDisplayName(authentication));
        return "boards/form";
    }

    @PostMapping
    public String createBoard(@ModelAttribute Board board,
                              RedirectAttributes redirectAttributes) {
        boardService.createBoard(board);
        redirectAttributes.addFlashAttribute("successMessage", "Board created successfully!");
        return "redirect:/boards";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model,
                               Authentication authentication) {
        Board board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        model.addAttribute("displayName", getDisplayName(authentication));
        return "boards/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateBoard(@PathVariable Long id,
                              @RequestParam String title,
                              @RequestParam String description,
                              RedirectAttributes redirectAttributes) {
        boardService.updateBoard(id, title, description);
        redirectAttributes.addFlashAttribute("successMessage", "Board updated successfully!");
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