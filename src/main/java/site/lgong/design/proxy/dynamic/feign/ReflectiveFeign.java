package site.lgong.design.proxy.dynamic.feign;

import site.lgong.design.proxy.dynamic.IWork;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReflectiveFeign {

    public <T> T newInstance(Class<T> tClass) {
        Map<Method, InvocationHandlerFactory.MethodHandler> methodToHandler = new LinkedHashMap<>();

        for (Method method : tClass.getMethods()) {
            if (method.getDeclaringClass() == Object.class) {
                continue;
            } else {
                DefaultMethodHandler handler = new DefaultMethodHandler();
                methodToHandler.put(method, handler);
            }
        }
        InvocationHandlerFactory factory = new InvocationHandlerFactory.Default();
        InvocationHandler handler = factory.create(methodToHandler);
        // 因为feignClient注解都是在接口上，所以使用jdk代理就可以了。
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[]{tClass}, handler);
    }

    public static void main(String[] args) {
        ReflectiveFeign reflectiveFeign = new ReflectiveFeign();
        // 构造一个小红
        IWork workProxy = reflectiveFeign.newInstance(IWork.class);
        workProxy.seekWork();
    }
}
