package site.lgong.design.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 *
 * @author yue.liu
 * @since [2021/8/13 9:52]
 */
public class DynamicProxy implements InvocationHandler {

    private Object mObject;//真实对象的引用

    public DynamicProxy(Object object) {
        this.mObject = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("before method " + method.getName());
        // 注意 这里传入的对象是mObject，而不是入参proxy
        Object result = method.invoke(mObject, args);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return result;
    }
}
