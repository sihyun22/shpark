package com.example.project.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;

import com.example.project.demo.domain.Comment;
import com.example.project.demo.domain.Post;
import com.example.project.demo.repository.CommentRepository;
import com.example.project.demo.repository.PostRepository;
import com.vane.badwordfiltering.BadWordFiltering;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.vane.badwordfiltering.*;

@Controller
@Log4j2
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    // @GetMapping("/list")
    // public String getMethodName(Model model) {
    // List<Post> posts = postRepository.findAll();
    // model.addAttribute("posts", posts);
    // return "list";
    // }

    @GetMapping("/list")
    public String showList(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Post> posts;
        if (keyword == null || keyword.isEmpty()) {
            posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        } else {
            posts = postRepository.findByTitleContainingOrWriterContainingOrContentContaining(keyword, keyword,
                    keyword);
        }
        model.addAttribute("posts", posts);
        return "list";
    }

    @GetMapping("/posts/register")
    public String registerPage(HttpServletRequest request, Model model) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken); // 수동으로 모델에 넣어줌
        return "register";
    }

    @PostMapping("/posts/postRegister")
    public String registerPost(@RequestParam("title") String title, @RequestParam("writer") String writer,
            @RequestParam("content") String content, Model model, RedirectAttributes redirectAttributes) {

        BadWordFiltering badWordFiltering = new BadWordFiltering();
        if (badWordFiltering.check(title) ||
                badWordFiltering.check(writer) ||
                badWordFiltering.check(content)) {

            redirectAttributes.addFlashAttribute("errorMessage", "비속어가 포함되어 있습니다. 다시 작성해주세요.");
            return "redirect:/posts/register";
        }
        Post post = new Post();
        post.setTitle(title);
        post.setWriter(writer);
        post.setContent(content);

        postRepository.save(post);
        return "redirect:/list";

    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/posts/modify")
    public String modifyPost(@RequestParam("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        post.change(title, content);
        postRepository.save(post);
        return "redirect:/list";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/posts/delete")
    public String deletePost(@RequestParam("id") Long id) {
        postRepository.deleteById(id);
        return "redirect:/list";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/posts/checkBadWords")
    @ResponseBody
    public Map<String, Object> checkBadWords(@RequestBody Map<String, String> params) {
        BadWordFiltering badWordFiltering = new BadWordFiltering();

        boolean hasBadWord = false;
        Map<String, String> errorMessages = new HashMap<>();

        String title = params.get("title");
        String writer = params.get("writer");
        String content = params.get("content");

        if (title != null && badWordFiltering.check(title)) {
            hasBadWord = true;
            errorMessages.put("title", "제목에 비속어가 포함되어 있습니다.");
        }

        if (writer != null && badWordFiltering.check(writer)) {
            hasBadWord = true;
            errorMessages.put("writer", "작성자에 비속어가 포함되어 있습니다.");
        }

        if (content != null && badWordFiltering.check(content)) {
            hasBadWord = true;
            errorMessages.put("content", "내용에 비속어가 포함되어 있습니다.");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("hasBadWord", hasBadWord);
        response.put("errors", errorMessages);

        return response;
    }

    private final CommentRepository commentRepository;

    @GetMapping("/posts/{id}")
    public String getPostWithComments(@PathVariable("id") Long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. ID: " + id));

        List<Comment> comments = commentRepository.findByPostIdOrderByRegDateAsc(id);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "/read";
    }

    @PostMapping("/comments")
    public String saveComment(@RequestParam("postId") Long postId, @RequestParam("writer") String writer,
            @RequestParam("content") String content) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setWriter(writer);
        comment.setContent(content);

        commentRepository.save(comment);

        return "redirect:/posts/" + postId;
    }

}
