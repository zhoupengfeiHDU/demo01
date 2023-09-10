package com.zpf.test.jdkProxy.business;

public class UserServiceImpl implements UserService {
    @Override
    public void getUsers() {
 
        try {
            /**
             * 这里让他睡一下，咱们这个业务逻辑只是输出一句话
             * 没有做真正的业务查询，不然运行太快
             */
            Thread.sleep(1000);
            System.out.println("查询了100个用户");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void deleteUser() {
        try {
            Thread.sleep(2000);
            System.out.println("删除了50个用户");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}