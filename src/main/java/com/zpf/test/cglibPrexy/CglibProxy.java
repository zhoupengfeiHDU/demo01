package com.zpf.test.cglibPrexy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    private Object proxy;

    public CglibProxy(Class clazz) {
        Enhancer enhancer = new Enhancer();
        //设置需要创建的子类
        enhancer.setSuperclass(clazz);
        //通过字节码技术动态创建子类实例
        enhancer.setCallback(this);
        this.proxy = enhancer.create();
    }

    public Object getProxy() {
        return proxy;
    }

    /**
     * methodProxy：增强的方法
     *
     * @param o           由CGlib创建的被代理的对象
     * @param method      触发的方法
     * @param args        方法入参
     * @param methodProxy 增强的方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before");
        Object result = methodProxy.invokeSuper(o, args);
        System.out.println("after");
        return result;
    }
}