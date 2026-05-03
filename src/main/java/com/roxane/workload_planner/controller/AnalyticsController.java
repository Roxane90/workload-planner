package com.roxane.workload_planner.controller;

import com.roxane.workload_planner.service.BoardService;
import com.roxane.workload_planner.service.TicketAssignmentService;
import com.roxane.workload_planner.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    private final TicketService ticketService;
    private final BoardService boardService;
    private final TicketAssignmentService assignmentService;

    public AnalyticsController(TicketService ticketService,
                               BoardService boardService,
                               TicketAssignmentService assignmentService) {
        this.ticketService = ticketService;
        this.boardService = boardService;
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public String showAnalytics(Model model) {
        // Ticket counts per status
        model.addAttribute("totalTickets", ticketService.countAll());
        model.addAttribute("todoCount", ticketService.countByStatus("TODO"));
        model.addAttribute("inProgressCount", ticketService.countByStatus("IN_PROGRESS"));
        model.addAttribute("doneCount", ticketService.countByStatus("DONE"));

        // Boards
        model.addAttribute("boards", boardService.getAllBoards());

        // Assignments per user
        model.addAttribute("assignmentsPerUser",
                assignmentService.getAssignmentCountPerUser());

        return "analytics/dashboard";
    }
}
