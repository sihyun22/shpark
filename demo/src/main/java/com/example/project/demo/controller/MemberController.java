package com.example.project.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;

import com.example.project.demo.domain.Member;
import com.example.project.demo.repository.MemberRepository;

import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signupSubmit(@RequestParam("uEmail") String uEmail, @RequestParam("uPw") String uPw,
            @RequestParam("uName") String uName) {

        String encodedPassword = passwordEncoder.encode(uPw);
        Member member = new Member();
        member.setUEmail(uEmail);
        member.setUPw(encodedPassword);
        member.setUName(uName);
        member.setRole("USER");
        memberRepository.save(member);

        return "redirect:/login";
    }


}
