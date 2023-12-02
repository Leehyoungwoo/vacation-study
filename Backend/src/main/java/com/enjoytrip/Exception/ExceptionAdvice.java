package com.enjoytrip.Exception;

import com.enjoytrip.response.ResponseMessage;
import com.enjoytrip.response.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ResponseMessage> handleLoginFailedException(LoginFailedException ex) {
        return new ResponseEntity<>(
                new ResponseMessage("로그인 실패. 아이디 혹은 비밀번호를 확인해주세요", null, StatusEnum.FAIL),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("유효하지 않은 값이 입력되었습니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>("리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}