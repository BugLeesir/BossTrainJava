package com.lyr.parser;


import com.alibaba.fastjson.JSON;
import com.lyr.exception.BusinessException;
import com.lyr.pojo.User;

import java.io.FileReader;
import java.io.IOException;
/**
 * @class JsonParser
 * @description json解析器,实现了Parser接口,重写了parseToUser方法,将json文件解析为User对象,并返回User对象
 * @author yunruili
 * @date 2024/01/14  04:52
 * @version 1.0.0
 */
public class JsonParser implements Parser{

    /**
     * @description 将json文件解析为User对象,并返回User对象
     * @author yunruili
     * @date 2024/01/14 05:31
     * @param filePath 文件路径
     * @return * @return: com.lyr.pojo.User
     */
    @Override
    public User parseToUser(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            // 读取所有的字符
            char[] buffer = new char[1024];
            int len = reader.read(buffer);
            String jsonString = new String(buffer, 0, len);
            // 将字符串解析为User对象
            return JSON.parseObject(jsonString, User.class);
        } catch (BusinessException | IOException e) {
            throw new BusinessException("Error parsing JSON to User: " + e.getMessage());
        }
    }

}