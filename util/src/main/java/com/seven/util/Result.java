package com.seven.util;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @ Description    返回数据处理
 * @ Date           2019-06-06 17:23
 */
public class Result implements Serializable {

    private static final long serialVersionUID = -3948389268046368059L;

    private int code;

    private String msg;

    private Object data;

    public Result(){};

    public Result(int code, String msg){};

    public static Result success() {
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("");
        result.setData("");
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(msg);
        result.setData("");
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("");
        result.setData(data);
        return result;
    }

    public static Result success(String msg, Object data) {
        Result result = new Result();
        result.setCode(HttpStatus.OK.value());
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result failure(String msg) {
        Result result = new Result();
        result.setCode(HttpStatus.CREATED.value());
        result.setMsg(msg);
        result.setData("");
        return result;
    }

    public static Result failure(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData("");
        return result;
    }

    public static Result failure(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }





}
