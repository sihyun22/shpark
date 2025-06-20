package com.example.project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.demo.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

    List<Comment> findByPostIdOrderByRegDateAsc(Long postId);
}