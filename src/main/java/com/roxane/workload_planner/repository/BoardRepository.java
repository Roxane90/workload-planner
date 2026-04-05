package com.roxane.workload_planner.repository;

import com.roxane.workload_planner.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}