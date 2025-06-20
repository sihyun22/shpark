package com.example.project.demo.controller;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.project.demo.domain.Member;
import com.example.project.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class GlobalModelAttributeAdvice {

    private final MemberRepository memberRepository;

@ModelAttribute("loginUser")
public Member addLoginUserToModel() {
    log.info("메서드 시작@@@@@@@");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
        log.info("비로그인 사용자 접근");
        return null;
    }

    String email = auth.getName();
    Member member = memberRepository.findByuEmail(email).orElse(null);

    if (member != null) {
        log.info("로그인 사용자: {}", member.getUName());
    } else {
        log.warn("DB에서 사용자 정보 찾을 수 없음: {}", email);
    }

    return member;
}
}
