package com.hsc.vince.androidmvp.net;

import com.google.gson.annotations.SerializedName;

/**
 * <p>作者：黄思程  2018/4/11 14:25
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：网络返回的基础类
 * <p>
 * 规范接口返回的数据格式
 * {
 * "code": 0,
 * "data"{},  / "data": []
 * "msg": "失败信息"
 * }
 */
class HttpStatus<T> {
    /*** 请求状态码*/
    @SerializedName("data")
    Long code;
    /***提示信息  请求出错时才会返回msg*/
    @SerializedName("msg")
    String msg;
    /*** 返回的数据*/
    @SerializedName("data")
    T data;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isCodeInvalid() {

        return code == 0;
    }
}
