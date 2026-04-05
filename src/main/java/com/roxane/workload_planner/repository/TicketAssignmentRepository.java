package com.roxane.workload_planner.repository;

import com.roxane.workload_planner.model.TicketAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketAssignmentRepository extends JpaRepository<TicketAssignment, Long> {
    List<TicketAssignment> findByTicketId(Long ticketId);
    List<TicketAssignment> findByUserId(Long userId);
}