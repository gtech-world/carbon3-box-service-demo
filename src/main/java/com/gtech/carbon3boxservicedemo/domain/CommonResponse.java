package com.gtech.carbon3boxservicedemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
public class CommonResponse<T> {

    public interface ResponseCode {
        int code = 0;
        String message = "";
        
        default int getCode() {
            return this.code;
        }

        default String getMessage() {
            return this.message;
        }

        static ResponseCode[] values() {
            return new ResponseCode[]{};
        }

        default List<String> allMessage() {
            return Arrays.stream(values()).map(i -> i.message).toList();
        }
    }

    @Getter
    @AllArgsConstructor
    public enum AuthResponse implements ResponseCode{ 

        INVALID_USER_PASSWORD(110001, "Invalid user password"),
        UNAUTHORIZED(110002, "Unauthorized");

        public final int code;

        public final String message;

    }

    @Getter
    @AllArgsConstructor
    public enum CommonResponseCode implements ResponseCode{

        SUCCESS(100000, "success"),
        INTERNAL_SYSTEM_ERROR(10001, "Internal system error"),
        SYSTEM_BUSY(10002, "The system is busy, please try again later"),
        NEED_AUTH(10003, "Need auth"),
        API_NOT_FOUND(10004, "Forbidden");

        public final int code;

        public final String message;

    }

    private Boolean success;

    private String message;

    private Integer code;

    @JsonIgnore
    private Integer httpStatus;

    private T data;

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<T>()
                .setSuccess(true)
                .setMessage(CommonResponseCode.SUCCESS.getMessage())
                .setCode(CommonResponseCode.SUCCESS.getCode())
                .setHttpStatus(HttpStatus.OK.value())
                .setData(data);
    }

    public static <T> CommonResponse<T> success() {
        return success(null);
    }

    public static <T> CommonResponse<T> fail(Integer code, HttpStatus httpStatus, String message) {
        return new CommonResponse<T>()
                .setSuccess(false)
                .setHttpStatus(httpStatus.value())
                .setCode(code)
                .setMessage(message);
    }

    public static <T> CommonResponse<T> fail(HttpStatus httpStatus, ResponseCode responseCode) {
        return fail(responseCode.getCode(), httpStatus, responseCode.getMessage());
    }

    public static <T> CommonResponse<T> fail(HttpStatus httpStatus, String message) {
        return fail(CommonResponseCode.INTERNAL_SYSTEM_ERROR.getCode(), httpStatus, message);
    }

    public static <T> CommonResponse<T> fail(String message) {
        return fail(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static <T> CommonResponse<T> fail() {
        return fail(HttpStatus.INTERNAL_SERVER_ERROR, CommonResponseCode.INTERNAL_SYSTEM_ERROR.getMessage());
    }

}
