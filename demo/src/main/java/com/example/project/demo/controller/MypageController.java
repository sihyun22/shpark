package com.example.project.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import com.example.project.demo.domain.Member;
import com.example.project.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MypageController {

    private final MemberRepository memberRepository;

    public String getLoggedInUserEmail(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @GetMapping("/mypage")
    public String showMyPage(org.springframework.ui.Model model){
        String email = getLoggedInUserEmail();
        log.info("현재 인증된 사용자 이메일: {}", email);
        Member member = memberRepository.findByuEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        model.addAttribute("user", member); 
        return "mypage";
    }
    
    
}
