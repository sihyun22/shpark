package com.example.project.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiMemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmailDuplicate(@RequestParam("uEmail") String uEmail) {
        boolean exists = memberRepository.existsByuEmail(uEmail);
        Map<String, Boolean> result = new HashMap<>();
        result.put("duplicate", exists);
        return result;
    }
}