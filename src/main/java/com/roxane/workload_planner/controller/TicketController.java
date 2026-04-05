package com.roxane.workload_planner.controller;

import com.roxane.workload_planner.model.Ticket;
import com.roxane.workload_planner.service.BoardService;
import com.roxane.workload_planner.service.TicketAssignmentService;
import com.roxane.workload_planner.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final BoardService boardService;
    private final TicketAssignmentService assignmentService;

    public TicketController(TicketService ticketService,
                            BoardService boardService,
                            TicketAssignmentService assignmentService) {
        this.ticketService = ticketService;
        this.boardService = boardService;
        this.assignmentService = assignmentService;
    }

    // Show tickets for a board
    @GetMapping("/board/{boardId}")
    public String getTicketsForBoard(@PathVariable Long boardId, Model model) {
        model.addAttribute("tickets", ticketService.getTicketsByBoard(boardId));
        model.addAttribute("board", boardService.getBoardById(boardId));
        return "tickets/list";
    }

    // Show form to create a new ticket
    @GetMapping("/new/{boardId}")
    public String showCreateForm(@PathVariable Long boardId, Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("boardId", boardId);
        return "tickets/form";
    }

    // Handle form submission
    @PostMapping("/{boardId}")
    public String createTicket(@PathVariable Long boardId,
                               @ModelAttribute Ticket ticket) {
        ticket.setBoard(boardService.getBoardById(boardId));
        ticketService.createTicket(ticket);
        return "redirect:/tickets/board/" + boardId;
    }

    // Update ticket status
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status) {
        Ticket ticket = ticketService.getTicketById(id);
        ticketService.updateStatus(id, status);
        return "redirect:/tickets/board/" + ticket.getBoard().getId();
    }

    // Delete a ticket
    @PostMapping("/{id}/delete")
    public String deleteTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        Long boardId = ticket.getBoard().getId();
        ticketService.deleteTicket(id);
        return "redirect:/tickets/board/" + boardId;
    }

    // Assign logged in user to ticket
    @PostMapping("/{id}/assign")
    public String assignToTicket(@PathVariable Long id,
                                 @RequestParam Long userId) {
        Ticket ticket = ticketService.getTicketById(id);
        assignmentService.assignUserToTicket(id, userId);
        return "redirect:/tickets/board/" + ticket.getBoard().getId();
    }
}