package com.zpf.test.cglibPrexy;

public class Client {
    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy(Dog.class);
        Dog dog = (Dog)proxy.getProxy();
        dog.run();
    }
}