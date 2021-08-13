package site.lgong.design.proxy.dynamic;

import site.lgong.design.proxy.statics.IRoom;
import site.lgong.design.proxy.statics.XiaoMing;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 代理类Client
 *
 * @author yue.liu
 * @since [2021/8/13 9:56]
 */
public class ProxyClient {

    /**
     * 优点
     * 1. 代理类在程序运行时由反射自动生成，无需我们手动编写代理类代码，简化编程工作
     * 2. 一个动态代理类InvocationHandler就能代理多个被代理类，较为灵活
     *
     * 缺点
     * 1、动态代理只能代理实现了接口的类，而不能代理实现抽象类的类
     * 2、通过反射调用被代理类的方法，效率低
     *
     **/

    public static void main(String[] args) {
        // 构造一个小红
        IWork xiaoHong = new XiaoHong();
        //构造一个动态代理
        InvocationHandler dynamicProxy = new DynamicProxy(xiaoHong);
        //获取被代理类小红的ClassLoader
        ClassLoader classLoader = xiaoHong.getClass().getClassLoader();

        // 通过Proxy类的newProxyInstance方法动态构造一个工作中介
        IWork workProxy = (IWork) Proxy.newProxyInstance(classLoader, new Class[]{IWork.class}, dynamicProxy);
        workProxy.seekWork();

        // 同样的，小明找房子也可以通过动态代理实现
        XiaoMing xiaoMing = new XiaoMing();
        InvocationHandler dynamicProxy1 = new DynamicProxy(xiaoMing);
        // classLoader可以用小红的，因为没有重新实现类加载器，两者本质用的是同一个类加载器
        IRoom roomProxy = (IRoom) Proxy.newProxyInstance(classLoader, new Class[]{IRoom.class}, dynamicProxy1);
        roomProxy.seekRoom();
    }

}
