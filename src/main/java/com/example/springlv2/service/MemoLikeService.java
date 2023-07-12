package com.example.springlv2.service;

import com.example.springlv2.dto.ApiResponseDto;
import com.example.springlv2.entity.Memo;
import com.example.springlv2.entity.MemoLike;
import com.example.springlv2.repository.MemoLikeRepository;
import com.example.springlv2.repository.MemoRepository;
import com.example.springlv2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoLikeService {

    private final MemoRepository memoRepository;
    private final MemoLikeRepository memoLikeRepository;

    public ApiResponseDto postMemoLike(Long memoId, UserDetailsImpl userDetails) {
        Memo memo = findMemo(memoId);
        MemoLike memoLike = memoLikeRepository.findByMemoAndUser(memo, userDetails.getUser()).orElse(null);

        // 한 번도 좋아요를 누르지 않았을 경우
        if (memoLike == null) {
            // 새로 좋아요 객체를 만들어
            memoLike = new MemoLike(memo, userDetails.getUser());
        } else if(!memoLike.isLiked()){      // 좋아요 했었지만 취소된 상태
            memoLike.setLiked(true);
        }
        // 저장
        memoLikeRepository.save(memoLike);
        return new ApiResponseDto("좋아요", HttpStatus.OK.value());
    }

    public ApiResponseDto updateMemoLike(Long memoId, UserDetailsImpl userDetails) {
        Memo memo = findMemo(memoId);
        MemoLike memoLike = memoLikeRepository.findByMemoAndUser(memo, userDetails.getUser()).orElse(null);

        if (memoLike == null) {
            throw new IllegalArgumentException("한 번도 좋아요 한 적이 없는 댓글입니다.");
        } else if(memoLike.isLiked()){      // 좋아요 했었지만 취소된 상태
            memoLike.setLiked(false);
        }
        // 저장
        memoLikeRepository.save(memoLike);

        return new ApiResponseDto("좋아요 취소", HttpStatus.OK.value());
    }

    public Memo findMemo(Long memoId) {
        return memoRepository.findById(memoId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }
}
