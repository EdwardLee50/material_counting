package com.cigarette.common.error;

/**
 * @author EdwardLee
 */
public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    /**
     * 直接接收EnumBusinessErrord的传参用于构造业务异常
     */
    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    /**
     * 接收自定义errorMessage的方式构造业务异常
     */
    public BusinessException(CommonError commonError,String errorMessage){
        super();
        this.commonError = commonError;
        this.commonError.setMessage(errorMessage);
    }

    public CommonError getCommonError() {
        return commonError;
    }

    public void setCommonError(CommonError commonError) {
        this.commonError = commonError;
    }

    @Override
    public int getCode() {
        return this.commonError.getCode();
    }

    @Override
    public String getMessage() {
        return this.commonError.getMessage();
    }

    @Override
    public CommonError setCode(Integer errorCode) {
        this.commonError.setCode(errorCode);
        return this;
    }

    @Override
    public CommonError setMessage(String errorMessage) {
        this.commonError.setMessage(errorMessage);
        return this;
    }
}
