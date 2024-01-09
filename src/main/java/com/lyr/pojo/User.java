package com.lyr.pojo;

/**
 * 用户类
 *
 * @author yunruili
 * @date 2024/01/08/23:33
 **/
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
