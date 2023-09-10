package com.zpf.test.jdkProxy.sale;

public class Lenovo implements SaleComputer {
    @Override
    public String pay(double money) {
 
        System.out.println("客户支付了"+money+"买了一台电脑");
        return "联想电脑";
    }
 
    @Override
    public void show() {
        System.out.println("展示电脑");
    }
}