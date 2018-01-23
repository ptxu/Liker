package com.liker.services.http.common;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * @ClassName: ResponseResult
 * @Description: ResponseResult
 * @author xuepngtao
 * @date 2018年1月12日 下午2:20:39
 *
 * @param <T>
 */
public class ResponseResult<T> implements Serializable {

    /**
     * @Fields serialVersionUID : serialVersionUID
     */
    private static final long serialVersionUID = -8726786221548792280L;

    /**
     * gson
     */
    private static Gson GSON = new Gson();

    // 状态码
    private int Code;

    // 响应的数据
    private T Data;

    // 消息
    private String Message;

    public ResponseResult() {

    }

    public ResponseResult(ResponseCode code) {
        Code = code.getValue();
        Message = code.getDesc();
    }

    public ResponseResult(ResponseCode code, T data) {
        Code = code.getValue();
        Message = code.getDesc();
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
        Message = ResponseCode.getTypeByValue(code).getDesc();
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String toJson() {
        return GSON.toJson(this);
    }
}
