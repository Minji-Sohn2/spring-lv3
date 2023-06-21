package com.example.springlv2.entity;

import com.example.springlv2.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    public Memo(MemoRequestDto memoRequestDto) {
        this.title = memoRequestDto.getTitle();
        this.username = memoRequestDto.getUsername();
        this.contents = memoRequestDto.getContents();
        this.password = memoRequestDto.getPassword();
    }

    // Entity class는 Db와 직접적으로 연관됨
    // 수정이 많이 발생하면 좋지 않음
    // 변수가 추가될 때마다 메서드가 수정되어야하기 때문에
    // update라는 함수로 묶어 전체를 셋팅하기 보다
    // 각각의 외부 set 메서드 사용하는 것이 좋음
    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    // memo에 저장된 password와 사용자가 입력한 inputPassword가 일치하는지 확인
    // 튜터님 코드 참고
    public void checkPassword(String inputPassword) {
        if(!password.equals(inputPassword)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}