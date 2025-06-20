package com.example.project.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.demo.domain.Member;



public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByuEmail(String uEmail);

    boolean existsByuEmail(String uEmail);
}