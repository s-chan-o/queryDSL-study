package study.querydsl.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Auth & JWT
    EXPIRED_TOKEN(401, "만료된 토큰입니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(401, "만료된 리프레시 토큰입니다."),
    NOT_FOUND_REFRESH_TOKEN(404, "존재하지 않는 리프레시 토큰입니다."),
    UNAUTHORIZED(401, "인증되지 않은 사용자입니다."),
    TOKEN_MALFORMED(400, "형식이 잘못된 토큰입니다."),
    MISSING_TOKEN(400, "토큰이 누락되었습니다."),
    FAILED_JWT_AUTH(401, "JWT 인증에 실패했습니다."),
    REFRESH_TOKEN_MISSING(400, "리프레시 토큰이 없습니다."),
    FORBIDDEN_ACCESS(403, "접근이 거부되었습니다."),

    // User
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(409, "이미 가입된 사용자입니다."),
    INVALID_PASSWORD(401, "비밀번호가 일치하지 않습니다."),
    INVALID_EMAIL_FORMAT(400, "이메일 형식이 잘못되었습니다."),
    INVALID_PASSWORD_FORMAT(400, "비밀번호는 8자 이상이며 영문과 숫자를 포함해야 합니다."),
    PRINCIPAL_INVALID(401, "현재 인증된 사용자 정보가 유효하지 않습니다."),

    // Store
    STORE_NOT_FOUND(404, "가게를 찾을 수 없습니다."),
    STORE_ALREADY_EXISTS(409, "이미 등록된 가게입니다."),

    // Menu
    MENU_NOT_FOUND(404, "메뉴를 찾을 수 없습니다."),
    MENU_LIMIT_EXCEEDED(400, "등록 가능한 메뉴 개수를 초과했습니다."),

    // Order
    ORDER_NOT_FOUND(404, "주문을 찾을 수 없습니다."),
    ORDER_ITEM_EMPTY(400, "주문 항목이 비어 있습니다."),
    ORDER_ALREADY_COMPLETED(400, "이미 완료된 주문입니다."),

    // Delivery
    DELIVERY_NOT_FOUND(404, "배달 정보를 찾을 수 없습니다."),
    DELIVERY_ALREADY_ASSIGNED(409, "이미 배달이 배정된 주문입니다."),
    DELIVERY_ALREADY_COMPLETED(400, "이미 완료된 배달입니다."),

    // 서버 공통
    REDIS_ERROR(500, "Redis 오류가 발생했습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다."),

    // 공통
    INVALID_INPUT(400, "잘못된 요청입니다."),
    FILE_EXTENSION_INVALID(400, "허용되지 않은 파일 확장자입니다.");

    private final int status;
    private final String message;
}
