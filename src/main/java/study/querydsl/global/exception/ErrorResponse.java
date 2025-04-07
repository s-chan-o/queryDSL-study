package study.querydsl.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        HttpStatus httpStatus = HttpStatus.valueOf(errorCode.getStatus());
        return ResponseEntity
                .status(httpStatus)
                .body(ErrorResponse.builder()
                        .status(httpStatus.value())
                        .error(httpStatus.getReasonPhrase())
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build());
    }
}

