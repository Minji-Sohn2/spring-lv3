package com.example.springlv2.service;

import com.example.springlv2.dto.MemoRequestDto;
import com.example.springlv2.dto.MemoResponseDto;
import com.example.springlv2.entity.Memo;
import com.example.springlv2.repository.MemoRepository;
import com.example.springlv2.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    // final 로 선언했기 때문에 처음부터 초기화 되어야 함
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }


    public MemoResponseDto createMemo(MemoRequestDto memoRequestDto, UserDetailsImpl userDetails) {

        // RequestDto -> Entity
        Memo memo = memoRepository.save(new Memo(memoRequestDto, userDetails.getUser()));

        // Entity -> ResponseDto
        return new MemoResponseDto(memo);
    }

    public List<MemoResponseDto> getMemoList() {

        return memoRepository.findAllByOrderByModifiedAtDesc().stream().map(MemoResponseDto::new).toList();
    }

    public MemoResponseDto getMemo(Long id) {
        return new MemoResponseDto(findMemo(id));
    }

    @Transactional
    public MemoResponseDto updateMemo(Long id, MemoRequestDto memoRequestDto, UserDetailsImpl userDetails) {

        Memo memo = findMemo(id);

        memo.checkUsername(userDetails.getUsername());

        memo.setTitle(memoRequestDto.getTitle());
        memo.setContents(memoRequestDto.getContents());

        return new MemoResponseDto(memo);
    }

    @Transactional
    public void deleteMemo(Long id, UserDetailsImpl userDetails) {

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);
        memo.checkUsername(userDetails.getUsername());

        memoRepository.delete(memo);
    }

    @Transactional
    public MemoResponseDto updateMemoAdmin(Long id, MemoRequestDto memoRequestDto) {

        Memo memo = findMemo(id);

        memo.setTitle(memoRequestDto.getTitle());
        memo.setContents(memoRequestDto.getContents());

        return new MemoResponseDto(memo);
    }

    @Transactional
    public void deleteMemoAdmin(Long id) {

        Memo memo = findMemo(id);

        memoRepository.delete(memo);
    }

    public Memo findMemo(Long id){

        Memo memo = memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
        return memo;
    }
}
