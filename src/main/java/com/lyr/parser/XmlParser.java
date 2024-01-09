package com.lyr.parser;


import com.lyr.pojo.User;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
/**
 * xml转换
 *
 * @author yunruili
 * @date 2024/01/08/23:35
 **/
public class XmlParser {
    private XmlParser() {
        throw new IllegalStateException("Utility class");
    }
    public static User parseXmlToUser(String filePath) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(filePath));

            return getUser(document);

        } catch (Exception e) {
            return null;
        }
    }

    private static User getUser(Document document) {
        Element root = document.getRootElement();
        String name = root.elementText("name");
        int age = Integer.parseInt(root.elementText("age"));
        String phoneNumber = root.elementText("phoneNumber");
        String address = root.elementText("address");

        return new User(name, age, phoneNumber, address);
    }
}