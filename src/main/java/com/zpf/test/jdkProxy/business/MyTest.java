package com.zpf.test.jdkProxy.business;

public class MyTest {
    public static void main(String[] args) {
        UserService userService = ProxyUtil.getProxy(new UserServiceImpl());
        userService.getUsers();
        userService.deleteUser();
    }
}