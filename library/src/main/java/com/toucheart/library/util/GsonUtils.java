package com.toucheart.library.util;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>作者：黄思程  2018/4/13 10:02
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：Gson解析工具类
 */
public class GsonUtils {
    //使用GsonBuilder可以对返回的数据进行各种格式化操作
    private static Gson gson = new GsonBuilder().
            setDateFormat("yyyy-MM-dd HH:mm:ss:SSS").create();

    /*** private constructor*/
    private GsonUtils() {
    }

    /**
     * 将json解析为指定的javaben对象
     *
     * @param jsonStr json字符串
     * @param clz     cls
     * @param <T>     泛型(数据类型)
     * @return parsed json T
     */
    public static <T> T json2Object(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, clz);
        } catch (Exception e) {
            LogUtils.e(e, "出错");
            return null;
        }
    }

    /**
     * JSON字符解析成<T>对象
     *
     * @param <T>
     * @param json
     * @param typeToken 对象类型，参数实例化方法：new TypeToken<TestVO>(){}
     * @return 对象
     */
    public static <T> T json2Object(String json, TypeToken<T> typeToken) {
        return gson.fromJson(json, typeToken.getType());
    }

    /**
     * 解析json数组为对应类型的List
     *
     * @param jsonArrayStr json str
     * @param clz          被转换的类转换的了的类
     * @param <T>          T list中存入的泛型对象
     * @return list<T>     解析的结果
     */
    public static <T> List<T> json2List(String jsonArrayStr, Class<T> clz) {
        try {
            List<T> list = new ArrayList<>();
            if (TextUtils.isEmpty(jsonArrayStr)) {
                return list;
            } else {
                JsonElement element = new JsonParser().parse(jsonArrayStr);
                if (!element.isJsonArray()) {
                    return list;
                }
                JsonArray array = element.getAsJsonArray();
                for (JsonElement jsonElement : array) {
                    list.add(gson.fromJson(jsonElement, clz));
                }
                return list;
            }
        } catch (Exception e) {
            LogUtils.e(e, "出错");
            return new ArrayList<>();
        }
    }

    /**
     * JSON字符解析成List<T>对象
     *
     * @param <T>
     * @param json
     * @param typeToken 对象类型，参数实例化方法：new TypeToken<List<TestVO>>(){}
     * @return 对象集合
     */
    public static <T> List<T> json2List(String json, TypeToken<List<T>> typeToken) {
        return gson.fromJson(json, typeToken.getType());
    }

    /**
     * 实体(对象/集合)转换成Json串
     *
     * @param obj
     * @return json字符串
     */
    public static String object2Json(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * 读取assets目录下的文本文件的数据
     *
     * @param context  程序上下文
     * @param fileName 文件名
     * @return String, 读取到的文本内容，失败返回""
     */
    public static String readAssets(Context context, String fileName) {
        String content = "";
        try {
            BufferedInputStream bufferedInputStream;
            byte[] bytes;
            try (InputStream inputStream = context.getAssets().open(fileName)) {
                bufferedInputStream = new BufferedInputStream(inputStream);
            }
            bytes = new byte[bufferedInputStream.available()];
            while (bufferedInputStream.read(bytes) > 0) {
                content = new String(bytes, "utf-8");
            }
        } catch (IOException e) {
            LogUtils.e(e, "出错");
        }
        return content;
    }
}
