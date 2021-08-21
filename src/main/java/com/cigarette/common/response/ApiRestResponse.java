package com.cigarette.common.response;

import com.cigarette.common.error.CommonError;
import lombok.Data;

/**
 * @author EdwardLee
 * 用于统一返回格式
 */
@Data
public class ApiRestResponse<T> {

    private static final long serialVersionUID = 1L;

    private static final int OK_CODE = 10000;

    private static final String OK_MSG = "SUCCESS";

    /**
     * 返回的状态码
     */
    private int code;

    /**
     * 返回的信息描述
     */
    private String message;

    /**
     * 返回的数据
     */
    private T data;

    private ApiRestResponse() {

    }

    public static <T> ApiRestResponse<T> success() {
        return ApiRestResponse.create(OK_CODE, OK_MSG);
    }

    public static <T> ApiRestResponse<T> success(T result) {
        return ApiRestResponse.create(OK_CODE, OK_MSG, result);
    }

    public static <T> ApiRestResponse<T> error(int statusCode,String message) {
        return ApiRestResponse.create(statusCode, message);
    }

    public static <T> ApiRestResponse<T> error(CommonError commonError) {
        return ApiRestResponse.create(commonError.getCode(), commonError.getMessage());
    }

    /**
     * 定义一个通用的创建方法
     * @param status 状态码
     * @param message 对于错误或异常的消息描述
     * @param data 具体的返回数据
     * @param <T> data的类型
     * @return 统一的返回对象
     */
    public static <T> ApiRestResponse<T> create(int status, String message, T data) {
        ApiRestResponse<T> apiRestResponse = new ApiRestResponse<>();
        apiRestResponse.setCode(status);
        apiRestResponse.setMessage(message);
        apiRestResponse.setData(data);
        return apiRestResponse;
    }

    public static <T> ApiRestResponse<T> create(Integer status, String message) {
        ApiRestResponse<T> apiRestResponse = new ApiRestResponse<>();
        apiRestResponse.setCode(status);
        apiRestResponse.setMessage(message);
        return apiRestResponse;
    }
}
