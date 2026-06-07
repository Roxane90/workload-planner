package com.roxane.workload_planner.service;

import com.roxane.workload_planner.model.Ticket;
import com.roxane.workload_planner.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {

        this.ticketRepository = ticketRepository;
    }

    //Get tickets per board
    public List<Ticket> getTicketsByBoard(Long boardId) {

        return ticketRepository.findByBoardId(boardId);
    }

    //Get tickets by ID
    public Ticket getTicketById(Long id) {

        return ticketRepository.findById(id).orElseThrow();
    }

    //Create new ticket
    public Ticket createTicket(Ticket ticket) {

        return ticketRepository.save(ticket);
    }

    //Update a ticket's status
    public Ticket updateStatus(Long id, String status) {
        Ticket ticket = getTicketById(id);
        ticket.setStatus(status);
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    //Delete a ticket
    public void deleteTicket(Long id) {

        ticketRepository.deleteById(id);
    }

    //Update a ticket
    public Ticket updateTicket(Long id, String title, String description, Integer storyPoints) {
        Ticket ticket = getTicketById(id);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setStoryPoints(storyPoints);
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    //Count by status (analytics)
    public long countByStatus(String status) {
        return ticketRepository.findAll().stream()
                .filter(t -> t.getStatus().equals(status))
                .count();
    }

    public long countAll() {
        return ticketRepository.count();
    }
}