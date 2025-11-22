package sopt_collaboration_team4.melon.global.exception;

import static sopt_collaboration_team4.melon.global.exception.ErrorCode.BAD_REQUEST;
import static sopt_collaboration_team4.melon.global.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static sopt_collaboration_team4.melon.global.exception.ErrorCode.METHOD_NOT_ALLOWED;
import static sopt_collaboration_team4.melon.global.exception.ErrorCode.VALIDATION_ERROR;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import sopt_collaboration_team4.melon.global.api.ApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.warn("ApiException 발생: {}", ex.getMessage());
        return buildErrorResponse(errorCode, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.warn("요청 값 검증 실패: {}", errors);
        return buildErrorResponse(VALIDATION_ERROR, VALIDATION_ERROR.getMessage(), errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.putIfAbsent(violation.getPropertyPath().toString(), violation.getMessage());
        }
        log.warn("ConstraintViolationException: {}", errors);
        return buildErrorResponse(VALIDATION_ERROR, VALIDATION_ERROR.getMessage(), errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String detailMessage = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        log.warn("HttpMessageNotReadableException: {}", detailMessage);
        return buildErrorResponse(BAD_REQUEST, BAD_REQUEST.getMessage(), "요청 본문을 읽을 수 없습니다.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("'%s' 파라미터를 %s 타입으로 변환할 수 없습니다.",
                ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "지정된");
        log.warn("MethodArgumentTypeMismatchException: {}", message);
        return buildErrorResponse(BAD_REQUEST, BAD_REQUEST.getMessage(), message);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.warn("허용되지 않은 HTTP 메서드 호출: {}", ex.getMethod());
        return buildErrorResponse(METHOD_NOT_ALLOWED, METHOD_NOT_ALLOWED.getMessage(), ex.getMethod());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleUnexpectedException(Exception ex) {
        log.error("예상치 못한 서버 오류", ex);
        return buildErrorResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
    }

    private <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(ErrorCode errorCode, String message, T detail) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(errorCode.getHttpStatus(), message, detail));
    }
}
