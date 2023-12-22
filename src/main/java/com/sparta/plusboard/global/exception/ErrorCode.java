package com.sparta.plusboard.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 베이스 에러"),
    BAD_PARAMETER(HttpStatus.BAD_REQUEST, "닉네임 또는 패스워드를 확인해주세요."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 정보 입니다."),
    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다.."),
    NO_AUTHORIZATION(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "작성자만 수정할 수 있습니다."),
    NOT_IMAGE_FILE(HttpStatus.BAD_REQUEST, "이미지 파일 형식이 아닙니다.");
    private HttpStatus status;
    private String message;

}
