package com.cigarette.common.error;

/**
 * @author EdwardLee
 * <p>
 */
public enum EnumBusinessError implements CommonError {
    /**
     * 20000 开头为通用错误类型
     */
    UNKNOWN_ERROR(20000, "未知错误"),
    PARAMETER_VALIDATION_ERROR(20001, "参数不合法"),
    FILE_NOT_EXISTS(20002,"文件不存在或异常"),
    FILE_FORMAT_ERROR(20003,"文件格式错误"),
    REQUEST_DUPLICATE_SUBMISSION(20004,"请求重复提交"),

    /**
     * 30000 开头为用户user相关错误定义
     */
    USER_CHECK_CODE_NOT_EXIT(30000, "验证码错误或不存在"),
    USER_NOT_EXISTS(30001, "用户不存在"),
    USER_TEL_EXISTS(3002, "同手机号用户已存在，详情请询问管理员"),
    USER_LOGIN_FAIL(30003, "用户手机不存在或密码不正确"),
    USER_NOT_LOGIN(30004, "相关用户未登录"),
    USER_PERMISSION_DENIED(3005, "用户权限错误，拒绝访问"),
    TOKEN_ILLEGAL(30006, "授权令牌非法或无效，请重新登录或联系管理员"),

    /**
     * 40000 开头为店铺shop相关错误定义
     */
    SELLER_NOT_EXIT(40001,"店铺不存在"),

    /**
     * 5000 开头为物料material和record相关错误定义
     */
    MATERIAL_NOT_EXIT(50001,"物料不存在"),

    MATERIAL_ORDER_NOT_EXIT(50002,"物料记录不存在"),

            ;

    int errorCode;
    String errorMessage;

    EnumBusinessError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }

    @Override
    public CommonError setCode(Integer errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    @Override
    public CommonError setMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

}
