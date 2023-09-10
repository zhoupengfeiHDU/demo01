package com.zpf.test.jdkProxy.sale;

/**
 * 真实对象和代理对象实现相同的卖电脑接口
 */
public interface SaleComputer {
 
    String pay(double money);
 
    void show();
}