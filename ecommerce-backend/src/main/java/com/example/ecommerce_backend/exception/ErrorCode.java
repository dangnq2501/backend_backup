package com.example.ecommerce_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Undefined error
    UNCATEGORIZED_EXCEPTION(9999, HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized Error"),

    // Developer error (1xxx)
    INVALID_KEY(1001, HttpStatus.INTERNAL_SERVER_ERROR, "Key error"),

    // Client error
    // Input error (2xxx)
    USERNAME_INVALID(201, HttpStatus.BAD_REQUEST, "Username must be at least 4 characters"),
    PASSWORD_INVALID(202, HttpStatus.BAD_REQUEST, "Password must be at least 8 characters"),
    THE_SAME_PASSWORD(203, HttpStatus.BAD_REQUEST, "New Password Is The Same As Old Password"),
    PAYMENT_INVALID(204, HttpStatus.BAD_REQUEST, "Payment MUST BE AT LEAST 0"),
    QUANTITY_INVALID(205, HttpStatus.BAD_REQUEST, "Quantity MUST BE AT LEAST 1"),
    QUANTITY_GREATER_THAN_STOCK(206, HttpStatus.BAD_REQUEST, "Quantity is greater than stock"),
    VERIFY_CODE_INCORRECT(207, HttpStatus.BAD_REQUEST, "Verify Code Incorrect"),
    // Existed error (2xxx)
    USER_EXISTED(208, HttpStatus.BAD_REQUEST, "User Existed"),
    TYPE_EXISTED(209, HttpStatus.BAD_REQUEST, "Type Existed"),
    SUB_TYPE_EXISTED(210, HttpStatus.BAD_REQUEST, "Sub Type Existed"),
    PRODUCT_EXISTED(211, HttpStatus.BAD_REQUEST, "Product Existed"),
    CUSTOMER_DETAIL_EXISTED(212, HttpStatus.BAD_REQUEST, "Customer Detail Existed"),
    VERIFY_CODE_EXPIRED(213, HttpStatus.BAD_REQUEST, "Verify Code Expired, New Code Has Been Sent"),
    // Other (2xxx)
    PAYMENT_NOT_SUCCEED(214, HttpStatus.BAD_REQUEST, "Payment Not Succeed"),
    // Unauthenticated error (3xxx)
    UNAUTHENTICATED(301, HttpStatus.UNAUTHORIZED, "Authentication Failed"),
    INVALID_TOKEN(302, HttpStatus.UNAUTHORIZED, "Invalid Token"),
    ACCOUNT_NOT_VERIFIED(303, HttpStatus.UNAUTHORIZED, "Account Not Verified"),
    PASSWORD_INCORRECT(304, HttpStatus.UNAUTHORIZED, "Password Incorrect"),
    // Unauthorized error (4xxx)
    UNAUTHORIZED(401, HttpStatus.FORBIDDEN, "Don't have permission"),
    // Not Found error (5xxx)
    USER_NOT_EXISTED(501, HttpStatus.NOT_FOUND, "User Not Existed"),
    TYPE_NOT_EXISTED(502, HttpStatus.NOT_FOUND, "Type Not Existed"),
    SUBTYPE_NOT_EXISTED(503, HttpStatus.NOT_FOUND, "SubType Not Existed"),
    PRODUCT_NOT_EXISTED(504, HttpStatus.NOT_FOUND, "Product Not Existed"),
    ROLE_NOT_EXISTED(505, HttpStatus.NOT_FOUND, "Role Not Existed"),
    CUSTOMER_DETAIL_NOT_EXISTED(506, HttpStatus.NOT_FOUND, "Customer Detail Not Existed"),
    CART_NOT_EXISTED(507, HttpStatus.NOT_FOUND, "Cart Not Existed"),
    CART_DETAIL_NOT_EXISTED(508, HttpStatus.NOT_FOUND, "Cart Detail Not Existed"),
    ORDER_NOT_EXISTED(509, HttpStatus.NOT_FOUND, "Order Not Existed"),
    CART_ITEM_NOT_EXISTED(510, HttpStatus.NOT_FOUND, "Cart item Not Existed"),
    PRODUCT_OUT_OF_STOCK(511, HttpStatus.NOT_FOUND, "Product Out of Stock"),
    ;

    final int code;
    final HttpStatus httpStatus;
    final String message;

}
