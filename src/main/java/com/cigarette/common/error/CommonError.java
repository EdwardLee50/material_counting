package com.cigarette.common.error;

/**
 * @author EdwardLee
 */
public interface CommonError {

    int getCode();

    String getMessage();

    CommonError setCode(Integer errorCode);

    CommonError setMessage(String errorMessage);
}
