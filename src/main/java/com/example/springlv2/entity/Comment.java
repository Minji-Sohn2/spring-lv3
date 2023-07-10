package com.example.springlv2.entity;

import com.example.springlv2.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "memo_id")
    private Memo memo;

    public Comment(Memo memo, CommentRequestDto requestDto, User user) {
        this.comment = requestDto.getComment();
        this.user = user;
        this.username = user.getUsername();
    }

    public void checkUsername(String inputUsername) {
        if(!username.equals(inputUsername)) {
            throw new IllegalArgumentException("자신이 작성한 메모만 수정/삭제할 수 있습니다.");
        }
    }
}
