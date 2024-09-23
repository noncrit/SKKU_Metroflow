package com.metroflow.repository;

import com.metroflow.model.dto.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
