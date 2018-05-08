package com.vince.news.net

import android.text.TextUtils
import okhttp3.*
import okio.Buffer


/**
 * <p>作者：黄思程  2017/11/17 17:16
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：请求参数拦截器
 */
class ParamsInterceptor : Interceptor {

    /*** 请求参数*/
    var queryParamsMap: MutableMap<String, String> = mutableMapOf()
    var paramsMap: MutableMap<String, String> = mutableMapOf()
    var headParamsMap: MutableMap<String, String> = mutableMapOf()
    var headerLinesList: MutableList<String> = mutableListOf()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder()

        //process header params inject
        val headerBuilder = request.headers()!!.newBuilder()
        headParamsMap = HttpManager.addHeader()
        if (headParamsMap.isNotEmpty()) {
            headParamsMap.entries.forEach {
                headerBuilder.add(it.key, it.value)
            }
        }

        if (!headerLinesList.isEmpty()) {
            headerLinesList.forEach {
                headerBuilder.add(it)
            }
        }

        requestBuilder.headers(headerBuilder.build())

        //process queryParams inject
        if (queryParamsMap.isNotEmpty()) {
            request = injectParamsIntoUrl(request.url().newBuilder(), requestBuilder, queryParamsMap)
        }

        //process post body inject
        if (paramsMap.isNotEmpty() && request.method() == "POST") {
            if (request.body() is FormBody) {
                val newFormBodyBuilder = FormBody.Builder()
                paramsMap.entries.forEach {
                    newFormBodyBuilder.add(it.key, it.value)
                }

                val oldBody = request.body() as FormBody
                for (i in 0..oldBody.size()) {
                    newFormBodyBuilder.add(oldBody.name(i), oldBody.value(i))
                }

                requestBuilder.post(newFormBodyBuilder.build())
                request = requestBuilder.build()
            } else if (request.body() is MultipartBody) {
                val multiBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                paramsMap.entries.forEach {
                    multiBody.addFormDataPart(it.key, it.value)
                }

                val oldParts = (request.body() as MultipartBody).parts()
                oldParts.forEach { multiBody.addPart(it) }

                requestBuilder.post(multiBody.build())
                request = requestBuilder.build()
            }
        }

        return chain.proceed(request)
    }

    /*** 添加请求参数到URL中*/
    private fun injectParamsIntoUrl(newBuilder: HttpUrl.Builder, requestBuilder: Request.Builder,
                                    paramsMap: MutableMap<String, String>): Request {
        if (paramsMap.isNotEmpty()) {
            paramsMap.entries.forEach {
                newBuilder.addQueryParameter(it.key, it.value)
            }
        }

        requestBuilder.url(newBuilder.build())
        return requestBuilder.build()
    }

    /*** 判断是否能添加参数到body*/
    private fun canInjectIntoBody(request: Request): Boolean {
        if (!TextUtils.equals(request.method(), "POST")) {
            return false
        }
        val body = request.body() ?: return false
        val mediaType = body.contentType() ?: return false
        if (!TextUtils.equals(mediaType.subtype(), "x-www-form-urlencoded")) {
            return false
        }

        return true
    }

    /*** 将body转换为String*/
    private fun bodyToString(request: RequestBody): String {
        val copy: RequestBody = request
        val buffer = Buffer()
        copy.writeTo(buffer)

        return buffer.readUtf8()
    }

    /*** builder构建器*/
    class Builder {
        private var interceptor: ParamsInterceptor = ParamsInterceptor()

        fun addParam(key: String, value: String): Builder {
            interceptor.paramsMap.put(key, value)
            return this
        }

        fun addParamsMap(paramsMap: Map<String, String>): Builder {
            interceptor.paramsMap.putAll(paramsMap)
            return this
        }

        fun addHeaderParam(key: String, value: String): Builder {
            interceptor.headParamsMap.put(key, value)
            return this
        }

        fun addHeaderParamsMap(headerParamsMap: Map<String, String>): Builder {
            interceptor.headParamsMap.putAll(headerParamsMap)
            return this
        }

        fun addHeaderLine(headerLine: String): Builder {
            val index = headerLine.indexOf(":")
            if (index == -1) {
                throw IllegalArgumentException("Unexpected header: " + headerLine)
            }
            interceptor.headerLinesList.add(headerLine)
            return this
        }

        fun addHeaderLinesList(headerLinesList: List<String>): Builder {
            for (headerLine in headerLinesList) {
                val index = headerLine.indexOf(":")
                if (index == -1) {
                    throw IllegalArgumentException("Unexpected header: " + headerLine)
                }
                interceptor.headerLinesList.add(headerLine)
            }
            return this
        }

        fun addQueryParam(key: String, value: String): Builder {
            interceptor.queryParamsMap.put(key, value)
            return this
        }

        fun addQueryParamsMap(queryParamsMap: Map<String, String>): Builder {
            interceptor.queryParamsMap.putAll(queryParamsMap)
            return this
        }

        fun build(): ParamsInterceptor = interceptor
    }

}