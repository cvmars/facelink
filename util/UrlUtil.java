package com.youxiake.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hehaifeng on 2019/1/8.
 */

public class UrlUtil {

    public static class UrlEntity {
        /**
         * 基础url
         */
        public String baseUrl;
        /**
         * url参数
         */
        public Map<String, String> params;
    }

    /**
     * 解析url
     *
     * @param url
     * @return
     */
    public static UrlEntity parse(String url) {
        UrlEntity entity = new UrlEntity();
        if (url == null) {
            return entity;
        }
        url = url.trim();
        if (url.equals("")) {
            return entity;
        }
        String[] urlParts = url.split("\\?");
        entity.baseUrl = urlParts[0];
        //没有参数
        if (urlParts.length == 1) {
            return entity;
        }
        //有参数
        String[] params = urlParts[1].split("&");
        entity.params = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            entity.params.put(keyValue[0], param.substring(keyValue[0].length() + 1, param.length()));
        }

        return entity;
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
//        UrlEntity entity = parse(null);
//        System.out.println(entity.baseUrl + "\n" + entity.params);
//        entity = parse("http://www.123.com");
//        System.out.println(entity.baseUrl + "\n" + entity.params);
//        entity = parse("http://www.123.com?id=1");
//        System.out.println(entity.baseUrl + "\n" + entity.params);
//        entity = parse("http://www.123.com?id=1&name=小明");
//        System.out.println(entity.baseUrl + "\n" + entity.params);
    }
}
