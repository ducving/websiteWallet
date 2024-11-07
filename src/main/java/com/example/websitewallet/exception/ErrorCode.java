package com.example.websitewallet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001,"email đã tồn tại", HttpStatus.BAD_REQUEST),

    UNCATEGORIZED_EXCEPTION(9999," lỗi không có trong các ngoại lệ còn lại ", HttpStatus.INTERNAL_SERVER_ERROR),

    PASSWORD_INVALID(1002,"password phải trên 8 KÍ TỰ",HttpStatus.BAD_REQUEST),

    USERNAME_INVALID(1003,"username phải trên 3 kí tự",HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED_EXCEPTION(1004,"Sai mật khẩu",HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),

    USER_NOT_EXISTED(1005,"email không tồn tại",HttpStatus.NOT_FOUND),

    UNAUTHORIZED(1007,"You do not add permission",HttpStatus.FORBIDDEN),

    EMAIL_INVALID(1008,"phải có @gmail",HttpStatus.FORBIDDEN),

    INSUFFICIENT_FUNDS(1009,"số tiền của ví nhỏ hơn số tiền muốn chi",HttpStatus.BAD_REQUEST);




    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }


    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

}