package com.example.springlv2.dto;

import com.example.springlv2.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

// Memo class와 내용은 같지만 DB와 소통하는 클래스는 조심히 다뤄야 하기 때문에 만든다
// JPA 할 때 이유 다시 언급
@Getter
public class MemoResponseDto {
    private Long id;
    private String title;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.createdAt = memo.getCreatedAt();
        this.modifiedAt = memo.getModifiedAt();
    }
}
