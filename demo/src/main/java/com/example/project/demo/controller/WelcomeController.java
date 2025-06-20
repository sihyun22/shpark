package com.example.project.demo.controller;



import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.project.demo.domain.Member;
import com.example.project.demo.domain.Post;
import com.example.project.demo.repository.MemberRepository;
import com.example.project.demo.repository.PostRepository;





@Controller
@Log4j2
@RequiredArgsConstructor
public class WelcomeController {
    
    private final PostRepository postRepository;
    
    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "/layout/basic";
    }
    

    @GetMapping("/test")
    public void showTestPage() {

    }

    private final MemberRepository memberRepository;

    // public String getLoggedInUserEmail(){
    //     Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    //     if(principal instanceof UserDetails){
    //         return ((UserDetails) principal).getUsername();
    //     } else {
    //         return principal.toString();
    //     }
    // }

    @GetMapping("/home")
    public String showDashBoardPost(Model model) 
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByuEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        List<Post> recentPosts = postRepository.findTop5ByOrderByIdDesc();
        model.addAttribute("recentPosts", recentPosts);
        model.addAttribute("user", member);
        return "home";
    }

    @GetMapping("/login")
    public void showLoginPage(){

    }

    @GetMapping("/signup")
    public void showSingUpPage(){

    }
    
}
