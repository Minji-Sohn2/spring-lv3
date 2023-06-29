package com.example.springlv2.entity;

import com.example.springlv2.dto.CommentResponseDto;
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

    @OneToMany(mappedBy = "memo")
    private List<Comment> commentList = new ArrayList<>();

    public Memo(MemoRequestDto memoRequestDto, User user) {
        this.title = memoRequestDto.getTitle();
        this.username = user.getUsername();
        this.contents = memoRequestDto.getContents();
    }

    // Entity class는 Db와 직접적으로 연관됨
    // 수정이 많이 발생하면 좋지 않음
    // 변수가 추가될 때마다 메서드가 수정되어야하기 때문에
    // update라는 함수로 묶어 전체를 셋팅하기 보다
    // 각각의 외부 set 메서드 사용하는 것이 좋음
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void addCommentList(Comment comment) {
        this.commentList.add(comment);
        comment.setMemo(this);
    }

    // memo에 저장된 username과 userDetails의 username(inputUsername)이 일치하는지 확인
    public void checkUsername(String inputUsername) {
        if(!username.equals(inputUsername)) {
            throw new IllegalArgumentException("자신이 작성한 메모만 수정/삭제할 수 있습니다.");
        }
    }
}