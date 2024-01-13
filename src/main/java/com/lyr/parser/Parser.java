package com.lyr.parser;

import com.lyr.pojo.User;
/**
 * @class Parser
 * @description 解析器接口,提供了parseToUser方法
 * @author yunruili
 * @date 2024/01/14  05:16
 * @version 1.0.0
 */
public interface Parser {
    User parseToUser(String filePath);
}