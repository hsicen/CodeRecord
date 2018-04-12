package com.hsc.vince.androidmvp.net;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * <p>作者：黄思程  2018/4/11 13:47
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：Retrofit 自定义解析类（请求request和返回response解析）
 */
public class DecodeConvertFactory extends Converter.Factory {
    private final Gson gson;

    /***
     * @return 自定义解析工厂*/
    public static DecodeConvertFactory create() {

        return create(new Gson());
    }

    private static DecodeConvertFactory create(Gson gson) {

        return new DecodeConvertFactory(gson);
    }

    private DecodeConvertFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }

        this.gson = gson;
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        if (type == String.class) {
            return null;
        }

        return new DecodeResponseBodyConverter<>(gson, adapter);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new DecodeRequestBodyConverter<>(gson, adapter);
    }
}
