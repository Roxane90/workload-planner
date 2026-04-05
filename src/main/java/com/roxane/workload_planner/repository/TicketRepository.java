package com.roxane.workload_planner.repository;

import com.roxane.workload_planner.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByBoardId(Long boardId);
}