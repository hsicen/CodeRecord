package com.hsc.vince.androidmvp.net;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * <p>作者：黄思程  2018/4/11 13:56
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：Retrofit自定义返回解析(解密操作)
 */
class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public DecodeResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        //在这里进行加解密操作
        String response = value.string();
        HttpStatus<T> httpStatus = gson.fromJson(response, new TypeToken<HttpStatus<T>>() {
        }.getType());

        if (httpStatus.getCode() == null) {
            try {
                return adapter.fromJson(response);
            } finally {
                value.close();
            }
        }

        try {
            String data = new Gson().toJson(httpStatus.getData());
            if (!httpStatus.isCodeInvalid()) {
                value.close();
                throw new ApiException(httpStatus.getCode(), data, httpStatus.getMsg());
            }
            return adapter.fromJson(data);
        } finally {
            value.close();
        }
    }
}
