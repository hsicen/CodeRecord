package com.hsc.vince.androidmvp.net;

import android.os.Looper;
import android.text.TextUtils;

import com.toucheart.library.util.LogUtils;
import com.toucheart.library.util.ToastUtils;

import org.reactivestreams.Subscriber;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;

import retrofit2.HttpException;

/**
 * <p>作者：黄思程  2018/4/11 11:15
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：网络请求回调层处理
 */
public abstract class ApiCallback<M> implements Subscriber<M> {
    /*** 未知错误*/
    private static final long UNKOWN_EXCEPTION = -101L;
    /*** 网络错误*/
    private static final long HTTP_EXCEPTION = -100L;
    /*** 自定义忽略错误(在这里添加   定义一个类，定义各种错误码，然后将不需要处理的错误码添加在这个集合里)*/
    private Long[] mIgnoreArray = new Long[]{200L,};

    /*** 回调*/
    public ApiCallback() {
    }

    /*** 请求成功的回调
     * @param model 回调的数据*/
    public abstract void onSuccess(M model);

    /*** 请求失败的回调
     * @param code 错误码
     * @param errorMsg 错误信息*/
    public abstract void onFailed(long code, String errorMsg);

    /***请求完成的回调*/
    public abstract void onFinish();

    @Override
    public void onNext(M m) {
        //请求成功集中处理
        onSuccess(m);
    }

    @Override
    public void onError(Throwable t) {
        //请求错误集中处理(自定义错误处理)
        if (t instanceof HttpException) {
            //网络错误
            HttpException httpException = (HttpException) t;
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 404 || code == 500 || code == 503) {
                //无法连接到服务器   自定义操作
                LogUtils.d("无法连接到服务器");
            }

            onFailed(code, msg);
        } else if (t instanceof ApiException) {
            //接口错误自定义处理
            ApiException apiException = (ApiException) t;
            long code = apiException.code();
            String msg = apiException.msg();
            if (apiException.loginOverDue()) {
                toastException(code, msg);
                LogUtils.d("登录过期,请重新登录");
            }

            onFailed(code, msg);
        } else if (t instanceof UnknownHostException
                || t instanceof SocketTimeoutException) {
            //服务器错误
            onFailed(HTTP_EXCEPTION, t.getMessage());
        } else {
            toastException(0L, t.getMessage());
            onFailed(UNKOWN_EXCEPTION, "");
        }

        onFinish();
    }

    /*****
     * @param code    code
     * @param message msg*/
    private void toastException(Long code, String message) {
        if (Looper.myLooper() == null || needToast(code)) {
            return;
        }

        if (!TextUtils.isEmpty(message)) {
            ToastUtils.showShort(message);
        }
    }

    private boolean needToast(Long code) {

        return Arrays.asList(mIgnoreArray).contains(code);
    }

    @Override
    public void onComplete() {
        //请求完成集中处理
        onFinish();
    }
}
