package com.example.springlv2.entity;

import com.example.springlv2.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "memo2") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Memo extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @OneToMany(mappedBy = "memo", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "memo", cascade = CascadeType.REMOVE)
    private List<MemoLike> memoLikeList = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Memo(MemoRequestDto memoRequestDto, User user) {
        this.title = memoRequestDto.getTitle();
        this.username = user.getUsername();
        this.contents = memoRequestDto.getContents();
    }

    public void addCommentList(Comment comment) {
        this.commentList.add(comment);
        comment.setMemo(this);
    }

    public int checkLikeCount() {
        int count = 0;
        for (MemoLike memoLike : memoLikeList) {
            if (memoLike.isLiked()) {
                count++;
            }
        }
        return count;
    }
}