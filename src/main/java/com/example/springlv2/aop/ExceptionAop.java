package com.example.springlv2.aop;

import com.example.springlv2.entity.Memo;
import com.example.springlv2.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j(topic = "ExceptionAop")
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionAop {

    private final MemoRepository memoRepository;

    @Pointcut("execution(* com.example.springlv2.service.CommentService.*(..))")
    private void commentService() {}

/*
    @Pointcut("execution(* com.example.springlv2.security.JwtAuthorizationFilter.doFilterInternal())")
    private void doFilterInternal() {}

    @Before("commentService()")
    public Object findMemo(JoinPoint joinPoint) throws Throwable {
       for (Object arg : joinPoint.getArgs()) {
           if (arg instanceof Long memoId) {
               Memo memo = memoRepository.findById(memoId).orElseThrow(() ->
                       new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
           }
       }

    }
*/

}
