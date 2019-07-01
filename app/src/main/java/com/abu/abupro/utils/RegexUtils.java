package com.abu.abupro.utils;

import android.util.Patterns;

import java.util.regex.Matcher;

/**
 *  正则表达式验证类，实现密码，用户名的验证
 */
public class RegexUtils {
    //密码正则表达式，英文字母或数字，6到32位
    private static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,32}$";
    //英文用户名正则表达式，英文字母，数字，下划线，6到18位
    private static final String REGEX_USER_NAME_ENGLISH = "^[\\w]{6,17}$";
    //中文用户名正则表达式
    private static final String REGEX_USER_NAME_CHINESE = "[\\u4E00-\\u9FA5]*";

    /**
     *   用户名验证
     * @param userName
     * @return
     */
    public static boolean isValidUserName(String userName) {
        return userName.matches(REGEX_USER_NAME_ENGLISH) || userName.matches(REGEX_USER_NAME_CHINESE);
    }

    /**
     *    密码验证
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {
        return password.matches(REGEX_PASSWORD);
    }

    /**
     *   判断是否包含网址，有则返回，无则返回空
     * @param text
     * @return
     */
    public static String matchShareUrl(String text){
        Matcher matcher = Patterns.WEB_URL.matcher(text);
        if (matcher.find()){
            return matcher.group();
        }
        return "";
    }
}
