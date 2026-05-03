package com.roxane.workload_planner.service;

import com.roxane.workload_planner.model.Ticket;
import com.roxane.workload_planner.model.TicketAssignment;
import com.roxane.workload_planner.model.User;
import com.roxane.workload_planner.repository.TicketAssignmentRepository;
import com.roxane.workload_planner.repository.TicketRepository;
import com.roxane.workload_planner.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketAssignmentService {

    private final TicketAssignmentRepository assignmentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketAssignmentService(TicketAssignmentRepository assignmentRepository,
                                   TicketRepository ticketRepository,
                                   UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public TicketAssignment assignUserToTicket(Long ticketId, Long userId) {
        List<TicketAssignment> existing = assignmentRepository.findByTicketId(ticketId);
        for (TicketAssignment a : existing) {
            if (a.getUser().getId().equals(userId)) {
                return a;
            }
        }
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        TicketAssignment assignment = new TicketAssignment();
        assignment.setTicket(ticket);
        assignment.setUser(user);

        return assignmentRepository.save(assignment);
    }

    public List<TicketAssignment> getAssignmentsForTicket(Long ticketId) {
        return assignmentRepository.findByTicketId(ticketId);
    }

    public List<TicketAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
}