package com.example.project.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.project.demo.domain.Board;
import com.example.project.demo.domain.Member;
import com.example.project.demo.domain.Post;
import com.example.project.demo.repository.BoardRepository;
import com.example.project.demo.repository.MemberRepository;
import com.example.project.demo.repository.PostRepository;

@Controller
@Log4j2
@RequiredArgsConstructor
public class WelcomeController {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "/layout/basic";
    }

    @GetMapping("/test")
    public void showTestPage() {

    }

    private final MemberRepository memberRepository;

    @GetMapping("/home")
    public String showDashBoardPost(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (auth != null && auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken))
                        ? auth.getName()
                        : null;

        if (email != null) {
            Member member = memberRepository.findByuEmail(email)
                    .orElse(null);
            model.addAttribute("user", member);
        } else {
            log.info("비로그인 사용자가 /home 접근");
            model.addAttribute("user", null);
        }

        List<Post> recentPosts = postRepository.findTop5ByOrderByIdDesc();

        List<Board> activeNotices = boardRepository.findByStartTimeBeforeAndEndTimeAfter(LocalDateTime.now(), LocalDateTime.now());
        model.addAttribute("recentPosts", recentPosts);
        log.info("activeNotices@@@: {}", activeNotices);
        model.addAttribute("activeNotices", activeNotices);
                model.addAttribute("modalActiveNotices", activeNotices);
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSingUpPage() {
        return "signup";
    }

}
