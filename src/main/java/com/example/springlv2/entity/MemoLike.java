package com.example.springlv2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "memolikes")
@NoArgsConstructor
public class MemoLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "memo_id", nullable = false)
    private Memo memo;

    @Column(name = "isLiked")
    boolean isLiked;

    public MemoLike(Memo memo, User user) {
        this.user = user;
        this.memo = memo;
        this.isLiked = true;
    }
}
