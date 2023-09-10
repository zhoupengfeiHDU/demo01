package com.zpf.test.jdkProxy.business;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
 
public class ProxyUtil {
 
    public static UserService getProxy(UserServiceImpl obj) {
 
        return (UserService)Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //获取开始时间
                long start=System.currentTimeMillis();
                method.invoke(obj,null);
                //获取结束时间
                long end=System.currentTimeMillis();
                System.out.println(method.getName()+"方法耗时"+(end-start)/1000+"s");
                return obj;
            }
        });
    }
}