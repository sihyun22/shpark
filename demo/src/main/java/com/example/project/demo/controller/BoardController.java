package com.example.project.demo.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.project.demo.domain.Board;
import com.example.project.demo.repository.BoardRepository;




@Controller
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardRepository boardRepository;

    @GetMapping("/board")
    public void showBoardList(){

    }

    @PostMapping("/boardRegister")
    public String boardRegister(@RequestParam("title") String title, @RequestParam("content") String content,
             @RequestParam("startTime") LocalDateTime startTime, @RequestParam("endTime") LocalDateTime endTime) {
        
                Board board = new Board();
                board.setTitle(title);
                board.setContent(content);
                board.setStartTime(startTime);
                board.setEndTime(endTime);

                boardRepository.save(board);
        
        return "redirect:/board";
    }
    
    
}
