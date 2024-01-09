package com.lyr.parser;


import com.alibaba.fastjson.JSON;
import com.lyr.pojo.User;

import java.io.FileReader;
import java.io.IOException;
/**
 * json转换
 *
 * @author yunruili
 * @date 2024/01/08/23:38
 **/
public class JsonParser {

    private JsonParser() {
        throw new IllegalStateException("Utility class");
    }
    public static User parseJsonToUser(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            // 读取所有的字符
            char[] buffer = new char[1024];
            int len = reader.read(buffer);
            String jsonString = new String(buffer, 0, len);
            // 将字符串解析为User对象
            return JSON.parseObject(jsonString, User.class);
        } catch (IOException e) {
            return null;
        }
    }

}