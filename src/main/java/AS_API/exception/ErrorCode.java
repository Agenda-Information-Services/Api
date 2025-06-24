package AS_API.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 사용자 관련 에러 //
    USER_EMAIL_INVALID(BAD_REQUEST, "UE1", "잘못된 이메일 형식입니다."),
    USER_PASSWORD_INVALID(BAD_REQUEST, "UE2", "잘못된 비밀번호 형식입니다"),
    USER_INVALID_INPUT(BAD_REQUEST, "UE3", "잘못된 입력입니다."),
    USER_ALREADY_EXIST(BAD_REQUEST, "UE4", "가입된 사용자입니다."),
    USER_REGIST_FAILED(INTERNAL_SERVER_ERROR, "UE5", "회원가입에 실패했습니다."),
    USER_NOT_FOUND(NOT_FOUND, "UE6", "해당하는 사용자가 없습니다."),
    USER_NICKNAME_INVALID(BAD_REQUEST,"UE7", "닉네임이 올바르지 않습니다."),
    USER_PASSWORD_MISMATCH(UNAUTHORIZED, "UE8", "현재 비밀번호가 일치하지 않습니다."),
    NO_UPDATE_FIELD(BAD_REQUEST, "UE9", "변경할 항목이 없습니다."),
    REPORT_NOT_FOUND(NOT_FOUND, "R001", "해당 신고 내역을 찾을 수 없습니다."),




    // 권한 관련 에러 //
    NO_PERMISSION(FORBIDDEN, "AE1", "해당 요청에 대한 권한이 없습니다."),
    LOGIN_INFO_INVALID(UNAUTHORIZED, "AE2", "아이디 혹은 비밀번호가 올바르지 않습니다."),
    TOKEN_EXPIRED(BAD_REQUEST,"SA7","토큰이 만료되었습니다"),
    INVALID_TOKEN(UNAUTHORIZED,"SA5","유효하지 않은 토큰입니다"),
    INVALID_SIGNATURE(UNAUTHORIZED,"SA6","유효하지 않은 서명입니다"),
    EXIST_ACCESSTOKEN_BLACKLIST(HttpStatus.CONFLICT,"SAT1","이미 로그아웃한 사용자입니다.");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
