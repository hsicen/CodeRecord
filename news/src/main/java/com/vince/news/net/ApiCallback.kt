package com.vince.news.net

import android.os.Looper
import com.vince.news.util.LogUtil
import com.vince.news.util.ToastUtil
import retrofit2.HttpException
import rx.Subscriber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * <p>作者：黄思程  2017/11/17 16:28
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：网络请求回掉接口封装
 */
abstract class ApiCallback<M> : Subscriber<M>() {
    /*** 未知错误*/
    val UNKOWN_EXCEPTION = -101L
    /*** 网络错误*/
    val HTTP_EXCEPTION = -100L


    /*** 请求成功
     ** @param model 消费类型 */
    abstract fun onSuccess(model: M)

    /*** 请求失败
     * @param code 状态码
     * @param msg  信息  */
    abstract fun onFailure(code: Long, msg: String?)

    /*** 无论是成功还是失败都会Finish*/
    abstract fun onFinish()

    override fun onError(e: Throwable) {
        LogUtil.e(e, "网络请求出错")

        when (e) {
            is HttpException -> {
                val code = e.code().toLong()
                val msg = e.message
                if (code == 404L || code == 500L || code == 503L) {
                    cannotConnectToService(msg!!)
                }
                onFailure(code, msg)
            }

            is UnknownHostException, is SocketTimeoutException -> {
                cannotConnectToService(e.message!!)
                onFailure(HTTP_EXCEPTION, e.message)
            }

            else -> onFailure(UNKOWN_EXCEPTION, "")
        }

        onFinish()
    }

    override fun onNext(t: M) {
        onSuccess(t)
    }

    override fun onCompleted() {
        onFinish()
    }

    /*** 无法连接到服务器的处理*/
    private fun cannotConnectToService(msg: String) {
        if (Looper.myLooper() == null) {
            return
        }

        msg.let { ToastUtil.showShort(it) }
    }

}