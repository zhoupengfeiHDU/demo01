package com.zpf.test.jdkProxy.sale;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
 
/**
 * 增强代理对象测试
 */
public class ProxyTest {
    public static void main(String[] args) {
 
        Lenovo lenovo = new Lenovo();
        /*
         *  java.lang.reflect.Proxy提供用于创建动态代理类和实例的静态方法，大家可以查看JDK文档。
         *  1.Proxy.newProxyInstance()通过调用newInstance 方法创建代理实例。
         *  2.方法中有三个参数
         *      ClassLoader loader：定义代理类的类加载器（即真实对象）
         *      Class<?>[] interfaces：真实对象实现的接口数组
         *      InvocationHandler h：调用处理程序，执行方法。
         */
 
        SaleComputer proxy_lenovo = (SaleComputer) Proxy.newProxyInstance(lenovo.getClass().getClassLoader(),
                lenovo.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            //invoke方法是核心代理逻辑思想，代理对象调用的所有方法都会触发该方法执行
            //增强方式有三种:1.增强参数 2.增强返回值 3.增强方法体
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
 
                /*
                 * 三个参数
                 *      1.proxy：就是代理对象本身
                 *      2.method：代理对象调用的方法，被封装为的对象。简单说 谁在运行调用，这个method就是谁。
                 *      3.args：代理对象调用方法时，传递的实际参数
                 */
 
 
                    //增强参数
                if (method.getName().equals("pay")) {
                    //增强参数，代理需要赚取利润，代理修改了参数。
                    //这里args[0]代表方法的第一个参数。
                    double money = (double) args[0];
                    money = money * 1.2;
                    //增强方法体
                    System.out.println("买电脑专车接送");
 
                    //修改了方法参数 这里method.invok就是调用方法。
                    String computer = (String) method.invoke(lenovo, money);
 
                    //增强方法体
                    System.out.println("买电脑免费配送");
 
                    //增强返回值
                    return computer+"送鼠标垫";
                } else {
                    //如果没有，那么就原样输出
                    Object obj = method.invoke(lenovo, args);
                    return obj;
                }
            }
        });
 
 
        String computer = proxy_lenovo.pay(5000);
        System.out.println(computer);
 
        proxy_lenovo.show();

    }
}