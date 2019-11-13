package com.seven.util;

/**
 * @ Description
 * @ Date           2019-06-11 11:19
 */
public class MyException extends RuntimeException {


    private int code;
    private String error;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public MyException(){}

    public MyException(int code, String error) {
        this.code = code;
        this.error = error;
    }
    public MyException(String error) {
        this.code = 500;
        this.error = error;
    }


}
