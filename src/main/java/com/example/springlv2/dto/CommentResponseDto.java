package com.example.springlv2.dto;

import com.example.springlv2.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String username;
    private String comment;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.likeCount = comment.checkLikeCount();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

}
