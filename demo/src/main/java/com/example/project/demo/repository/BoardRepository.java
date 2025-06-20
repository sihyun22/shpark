package com.example.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.demo.domain.Board;
import java.util.List;
import java.time.LocalDateTime;


public interface BoardRepository extends JpaRepository<Board, Long>{

    List<Board> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime now1, LocalDateTime now2);
}

  