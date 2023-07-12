package com.example.springlv2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "commentlikes") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    Comment comment;

    @Column(name = "isLiked")
    boolean isLiked;

    public CommentLike(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
        this.isLiked = true;
    }
}
