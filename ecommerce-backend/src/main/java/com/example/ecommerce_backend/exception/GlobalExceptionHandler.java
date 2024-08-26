package com.example.ecommerce_backend.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.ecommerce_backend.dto.response.ApiResponse;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        apiResponse.setResult(e.getMessage());

        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getHttpStatus())
                .body(apiResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ApiResponse<AccessDeniedException>> handleAccessDeniedException() {
        ApiResponse<AccessDeniedException> apiResponse = new ApiResponse<>();

        apiResponse.setCode(ErrorCode.UNAUTHORIZED.getCode());
        apiResponse.setMessage(ErrorCode.UNAUTHORIZED.getMessage());

        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse<AppException>> handleAppException(AppException e) {
        ApiResponse<AppException> apiResponse = new ApiResponse<>();
        ErrorCode errorCode = e.getErrorCode();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<MethodArgumentNotValidException>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(e.getFieldError().getDefaultMessage());

        } catch (Exception exception) {

        }

        ApiResponse<MethodArgumentNotValidException> apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }
}
