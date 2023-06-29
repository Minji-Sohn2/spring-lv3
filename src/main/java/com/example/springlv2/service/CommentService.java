package com.example.springlv2.service;

import com.example.springlv2.dto.CommentRequestDto;
import com.example.springlv2.dto.CommentResponseDto;
import com.example.springlv2.entity.Comment;
import com.example.springlv2.entity.Memo;
import com.example.springlv2.repository.CommentRepository;
import com.example.springlv2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemoService memoService;
    public CommentResponseDto createComment(Long memoId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {

        Memo memo = memoService.findMemo(memoId);

        Comment comment = commentRepository.save(new Comment(memo, requestDto, userDetails.getUser()));

        return new CommentResponseDto(comment);
    }

}
