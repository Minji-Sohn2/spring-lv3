package com.example.springlv2.controller;

import com.example.springlv2.dto.CommentRequestDto;
import com.example.springlv2.dto.CommentResponseDto;
import com.example.springlv2.security.UserDetailsImpl;
import com.example.springlv2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{memoId}")
    public CommentResponseDto createComment(@PathVariable Long memoId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(memoId,requestDto, userDetails);
    }
}
