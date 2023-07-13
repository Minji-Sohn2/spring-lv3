package com.example.springlv2.entity;

import com.example.springlv2.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikeList = new ArrayList<>();

    public Comment(Memo memo, CommentRequestDto requestDto, User user) {
        this.comment = requestDto.getComment();
        this.user = user;
        this.username = user.getUsername();
        this.memo = memo;
    }

    public int checkLikeCount() {
        int count = 0;
        for (CommentLike commentLike : commentLikeList) {
            if (commentLike.isLiked()) {
                count++;
            }
        }
        return count;
    }
}
