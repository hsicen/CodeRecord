package com.toucheart.library.util;

/**
 * 作者：Toucheart  2017/9/2 17:01
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：String监测工具
 */
public class StringUtil {


    /**
     * 判断是不是英文字母
     *
     * @param charaString 待监测文本
     * @return boolean
     */
    public static boolean isEnglish(String charaString) {
        if (isSpace(charaString)) {
            return false;
        }
        return charaString.matches("^[a-zA-Z]*");
    }

    /**
     * 判断是不是数字
     *
     * @param charaString 待监测文本
     * @return boolean
     */
    public static boolean isNumber(String charaString) {
        if (isSpace(charaString)) {
            return false;
        }
        return charaString.matches("^[0-9]*");
    }

    /**
     * 是否为空白字符串
     *
     * @param s 文本
     * @return true：文本为空白字符串
     */
    public static boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
