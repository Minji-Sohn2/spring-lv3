package com.example.springlv2.service;

import com.example.springlv2.dto.MemoRequestDto;
import com.example.springlv2.dto.MemoResponseDto;
import com.example.springlv2.entity.Memo;
import com.example.springlv2.repository.MemoRepository;
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


    public MemoResponseDto createMemo(MemoRequestDto memoRequestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(memoRequestDto);

        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        return new MemoResponseDto(saveMemo);
    }

    public List<MemoResponseDto> getMemoList() {

        return memoRepository.findAllByOrderByModifiedAtDesc().stream().map(MemoResponseDto::new).toList();
    }

    public MemoResponseDto getMemo(Long id) {
        return new MemoResponseDto(findMemo(id));
    }

    @Transactional
    public MemoResponseDto updateMemo(Long id, MemoRequestDto memoRequestDto) {

        Memo memo = findMemo(id);

        memo.checkPassword(memoRequestDto.getPassword());

        memo.setTitle(memoRequestDto.getTitle());
        memo.setUsername(memoRequestDto.getUsername());
        memo.setContents(memoRequestDto.getContents());

        return new MemoResponseDto(memo);
    }

    public void deleteMemo(Long id, String password) {

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        memo.checkPassword(password);
        memoRepository.delete(memo);
    }

    private Memo findMemo(Long id){
        Memo memo = memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
        return memo;
    }
}
