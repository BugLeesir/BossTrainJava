package com.lyr.pojo;

/**
 * @class User
 * @description 用户类,包含用户的姓名,年龄,电话号码,地址等信息,提供了构造方法,toString方法,getter和setter方法,equals方法和hashCode方法
 * @author yunruili
 * @date 2024/01/14  05:16
 * @version 1.0.0
 */
public class User {
    private final String name;
    private final int age;
    private final String phoneNumber;
    private final String address;

    public User(String name, int age, String phoneNumber, String address) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    /**
     * @description 重写了toString方法,返回用户信息字符串
     * @author yunruili
     * @date 2024/01/14 05:48
     * @return * @return: java.lang.String
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

}
