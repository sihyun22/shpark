package com.example.project.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.project.demo.domain.Member;
import com.example.project.demo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByuEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found User Info"));

        String role = member.getRole();
        if (role == null || role.isEmpty()) {
            role = "USER"; // 혹은 기본 역할 지정
        }
        return User.builder()
                .username(member.getUEmail())
                .password(member.getUPw())
                .roles(member.getRole())
                .build();
    }

}
