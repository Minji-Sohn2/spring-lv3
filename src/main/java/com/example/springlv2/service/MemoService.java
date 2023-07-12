package com.example.springlv2.service;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.dto.CommentResponseDto;
import com.example.springlv2.dto.MemoRequestDto;
import com.example.springlv2.dto.MemoResponseDto;
import com.example.springlv2.entity.Memo;
import com.example.springlv2.entity.UserRoleEnum;
import com.example.springlv2.repository.CommentRepository;
import com.example.springlv2.repository.MemoRepository;
import com.example.springlv2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final CommentRepository commentRepository;

    public MemoResponseDto createMemo(MemoRequestDto memoRequestDto, UserDetailsImpl userDetails) {

        // RequestDto -> Entity
        Memo memo = memoRepository.save(new Memo(memoRequestDto, userDetails.getUser()));

        // Entity -> ResponseDto
        return new MemoResponseDto(memo);
    }

    public List<MemoResponseDto> getMemoList() {

        return memoRepository.findAllByOrderByModifiedAtDesc().stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public MemoResponseDto getMemo(Long id) {
        Memo memo = findMemo(id);
        List<CommentResponseDto> commentList = commentRepository.findAllByOrderByCreatedAtDesc().stream().map(CommentResponseDto::new).toList();

        return new MemoResponseDto(memo, commentList);
    }

    @Transactional
    public MemoResponseDto updateMemo(
            Long id,
            MemoRequestDto memoRequestDto,
            UserDetailsImpl userDetails
    ) {

        Memo memo = findMemo(id);
        List<CommentResponseDto> commentList = commentRepository.findAllByOrderByCreatedAtDesc().stream().map(CommentResponseDto::new).toList();

        if(userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN)) {
            memo.setTitle(memoRequestDto.getTitle());
            memo.setContents(memoRequestDto.getContents());
        } else {
            memo.checkUsername(userDetails.getUsername());

            memo.setTitle(memoRequestDto.getTitle());
            memo.setContents(memoRequestDto.getContents());
        }

        return new MemoResponseDto(memo, commentList);
    }

    @Transactional
    public ApiResponseDto deleteMemo(Long id, UserDetailsImpl userDetails) {

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        if(userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN)) {
            memoRepository.delete(memo);
        } else {
            memo.checkUsername(userDetails.getUsername());

            memoRepository.delete(memo);
        }

        return new ApiResponseDto("메모 삭제 완료", HttpStatus.OK.value());
    }

    @Transactional
    public void deleteMemoAdmin(Long id) {

        Memo memo = findMemo(id);

        memoRepository.delete(memo);
    }

    public Memo findMemo(Long id){

        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
    }
}
