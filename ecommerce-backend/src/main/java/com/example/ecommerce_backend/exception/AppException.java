package com.example.ecommerce_backend.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class AppException extends RuntimeException{
    private ErrorCode errorCode;
}
