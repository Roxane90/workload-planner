package com.roxane.workload_planner.controller;

import com.roxane.workload_planner.model.Ticket;
import com.roxane.workload_planner.model.User;
import com.roxane.workload_planner.repository.UserRepository;
import com.roxane.workload_planner.service.BoardService;
import com.roxane.workload_planner.service.TicketAssignmentService;
import com.roxane.workload_planner.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final BoardService boardService;
    private final TicketAssignmentService assignmentService;
    private final UserRepository userRepository;

    public TicketController(TicketService ticketService,
                            BoardService boardService,
                            TicketAssignmentService assignmentService,
                            UserRepository userRepository) {
        this.ticketService = ticketService;
        this.boardService = boardService;
        this.assignmentService = assignmentService;
        this.userRepository = userRepository;
    }

    @GetMapping("/board/{boardId}")
    public String getTicketsForBoard(@PathVariable Long boardId, Model model,
                                     org.springframework.security.core.Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username);

        model.addAttribute("tickets", ticketService.getTicketsByBoard(boardId));
        model.addAttribute("board", boardService.getBoardById(boardId));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("assignments", assignmentService.getAllAssignments());
        return "tickets/list";
    }

    @GetMapping("/new/{boardId}")
    public String showCreateForm(@PathVariable Long boardId, Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("boardId", boardId);
        return "tickets/form";
    }

    @PostMapping("/{boardId}")
    public String createTicket(@PathVariable Long boardId,
                               @ModelAttribute Ticket ticket,
                               RedirectAttributes redirectAttributes) {
        ticket.setBoard(boardService.getBoardById(boardId));
        ticketService.createTicket(ticket);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket created successfully!");
        return "redirect:/tickets/board/" + boardId;
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               RedirectAttributes redirectAttributes) {
        Ticket ticket = ticketService.getTicketById(id);
        ticketService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket status updated!");
        return "redirect:/tickets/board/" + ticket.getBoard().getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteTicket(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        Ticket ticket = ticketService.getTicketById(id);
        Long boardId = ticket.getBoard().getId();
        ticketService.deleteTicket(id);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket deleted successfully!");
        return "redirect:/tickets/board/" + boardId;
    }

    @PostMapping("/{id}/assign")
    public String assignToTicket(@PathVariable Long id,
                                 org.springframework.security.core.Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        Ticket ticket = ticketService.getTicketById(id);
        assignmentService.assignUserToTicket(id, user.getId());
        redirectAttributes.addFlashAttribute("successMessage", "You have been assigned to the ticket!");
        return "redirect:/tickets/board/" + ticket.getBoard().getId();
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        return "tickets/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateTicket(@PathVariable Long id,
                               @RequestParam String title,
                               @RequestParam String description,
                               RedirectAttributes redirectAttributes) {
        Ticket ticket = ticketService.getTicketById(id);
        ticketService.updateTicket(id, title, description);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket updated successfully!");
        return "redirect:/tickets/board/" + ticket.getBoard().getId();
    }

    @PostMapping("/{id}/unassign")
    public String unassignFromTicket(@PathVariable Long id,
                                     @RequestParam Long userId,
                                     org.springframework.security.core.Authentication authentication,
                                     RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username);
        Ticket ticket = ticketService.getTicketById(id);

        // Members can only unassign themselves
        // Managers can unassign anyone
        if (currentUser.getRole().equals("MANAGER") ||
                currentUser.getId().equals(userId)) {
            assignmentService.removeAssignment(id, userId);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Assignment removed successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "You can only remove your own assignment!");
        }

        return "redirect:/tickets/board/" + ticket.getBoard().getId();
    }
}