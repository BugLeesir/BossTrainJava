package com.lyr.parser;


import com.lyr.exception.BusinessException;
import com.lyr.pojo.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @class XmlParser
 * @description xml解析器,实现了Parser接口,重写了parseToUser方法,将xml文件解析为User对象,并返回User对象
 * @author yunruili
 * @date 2024/01/14  04:52
 * @version 1.0.0
 */
public class XmlParser implements Parser{
    /**
     * @description 将xml文件解析为User对象,并返回User对象
     * @author yunruili
     * @date 2024/01/14 05:28
     * @param filePath 文件路径
     * @return * @return: com.lyr.pojo.User
     */
    @Override
    public User parseToUser(String filePath) {
        try {
            SAXReader reader = new SAXReader();
            // 禁止外部实体注入
            reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            Document document = reader.read(new File(filePath));

            return getUser(document);// 将Document对象解析为User对象
        } catch (BusinessException | DocumentException | MalformedURLException | SAXException e) { // 处理异常
            throw new BusinessException("Error parsing XML to User: " + e.getMessage());
        }
    }

    /**
     * @description 将Document对象解析为User对象,并返回User对象
     * @author yunruili
     * @date 2024/01/14 05:29
     * @param document 文件对象
     * @return * @return: com.lyr.pojo.User
     */
    private static User getUser(Document document) {
        Element root = document.getRootElement();
        String name = root.elementText("name");
        int age = Integer.parseInt(root.elementText("age"));
        String phoneNumber = root.elementText("phoneNumber");
        String address = root.elementText("address");

        return new User(name, age, phoneNumber, address);
    }
}