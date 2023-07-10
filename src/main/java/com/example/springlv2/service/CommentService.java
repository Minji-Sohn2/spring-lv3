package com.example.springlv2.service;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.dto.CommentRequestDto;
import com.example.springlv2.dto.CommentResponseDto;
import com.example.springlv2.entity.Comment;
import com.example.springlv2.entity.Memo;
import com.example.springlv2.entity.UserRoleEnum;
import com.example.springlv2.repository.CommentRepository;
import com.example.springlv2.repository.MemoRepository;
import com.example.springlv2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemoRepository memoRepositoy;
    public CommentResponseDto createComment(Long memoId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {

        Memo memo = findMemo(memoId);

        Comment comment = new Comment(memo, requestDto, userDetails.getUser());
        memo.addCommentList(comment);

        return new CommentResponseDto(commentRepository.save(comment));
    }

    @Transactional
    public CommentResponseDto update(Long memoId, Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        findMemo(memoId);
        Comment comment = findComment(commentId);

        if(userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN)) {
            comment.setComment(requestDto.getComment());
        } else {
            comment.checkUsername(userDetails.getUsername());

            comment.setComment(requestDto.getComment());
        }

        return new CommentResponseDto(comment);
    }

    public ApiResponseDto deleteComment(Long memoId, Long commentId, UserDetailsImpl userDetails) {
        findMemo(memoId);
        Comment comment = findComment(commentId);

        if(userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN)) {
            commentRepository.delete(comment);
        } else {
            comment.checkUsername(userDetails.getUsername());

            commentRepository.delete(comment);
        }

        return new ApiResponseDto("댓글 삭제 완료", HttpStatus.OK.value());
    }

    private Memo findMemo(Long memoId) {
        return memoRepositoy.findById(memoId).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다."));
    }
}
