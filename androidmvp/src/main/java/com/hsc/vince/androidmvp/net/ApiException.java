package com.hsc.vince.androidmvp.net;

/**
 * <p>作者：黄思程  2018/4/11 11:15
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：自定义错误
 */
public class ApiException extends RuntimeException {
    private final long errorCode;
    private final String errorMsg;
    private final String data;

    /**
     * @param errorCode errorCode
     * @param errorMsg  errorMsg
     */
    public ApiException(long errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = "";
    }

    /**
     * @param errorCode errorCode
     * @param errorMsg  errorMsg
     * @param data      data
     */
    public ApiException(long errorCode, String errorMsg, String data) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public long code() {

        return errorCode;
    }

    public String msg() {

        return errorMsg;
    }

    public String data() {

        return data;
    }

    //自定义错误，如登录过期，账号在其他设备登录
    public boolean loginOverDue() {
        //登录过期的失败码
        return errorCode == 100001L;
    }
}
