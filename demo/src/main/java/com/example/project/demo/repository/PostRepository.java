package com.example.project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.demo.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByTitleContainingOrWriterContainingOrContentContaining(String title, String writer, String content);

	List<Post> findTop5ByOrderByIdDesc();
}