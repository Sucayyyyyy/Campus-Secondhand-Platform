package com.campus.util;

import java.io.Serializable;

/**
 * 统一的 API 响应结果封装类
 */
public class ResponseResult implements Serializable {

    private boolean success;
    private String message;
    private Object data;

    // --- 1. 构造方法（私有，强制使用静态方法创建） ---
    private ResponseResult(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // --- 2. 静态成功方法 ---
    public static ResponseResult success(String message, Object data) {
        return new ResponseResult(true, message, data);
    }

    public static ResponseResult success(String message) {
        return new ResponseResult(true, message, null);
    }

    // --- 3. 静态失败方法 ---
    public static ResponseResult error(String message) {
        return new ResponseResult(false, message, null);
    }

    // --- 4. Getters and Setters (或使用 Lombok) ---
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}