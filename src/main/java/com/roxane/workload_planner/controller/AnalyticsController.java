package com.roxane.workload_planner.controller;

import com.roxane.workload_planner.repository.UserRepository;
import com.roxane.workload_planner.service.BoardService;
import com.roxane.workload_planner.service.TicketAssignmentService;
import com.roxane.workload_planner.service.TicketService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController extends BaseController {

    private final TicketService ticketService;
    private final BoardService boardService;
    private final TicketAssignmentService assignmentService;

    public AnalyticsController(TicketService ticketService,
                               BoardService boardService,
                               TicketAssignmentService assignmentService,
                               UserRepository userRepository) {
        super(userRepository);
        this.ticketService = ticketService;
        this.boardService = boardService;
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public String showAnalytics(Model model, Authentication authentication) {
        model.addAttribute("totalTickets", ticketService.countAll());
        model.addAttribute("todoCount", ticketService.countByStatus("TODO"));
        model.addAttribute("inProgressCount", ticketService.countByStatus("IN_PROGRESS"));
        model.addAttribute("doneCount", ticketService.countByStatus("DONE"));
        model.addAttribute("boards", boardService.getAllBoards());
        model.addAttribute("assignmentsPerUser", assignmentService.getAssignmentCountPerUser());
        model.addAttribute("completedPerUser", assignmentService.getCompletedTicketsPerUser());
        model.addAttribute("displayName", getDisplayName(authentication));
        return "analytics/dashboard";
    }


}