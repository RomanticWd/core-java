package site.lgong.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法区出现OOM
 * 借助CGLib产生大量对象，对象的类名、修饰符、常量池会去填满方法区。
 * VM Args: -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
 */
public class JavaMethodAreaOOM {

    static class OOMObject {}

    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, objects);
                }
            });
            enhancer.create();
        }
        // 执行结果：
//        Caused by: java.lang.OutOfMemoryError: Metaspace
//        at java.base/java.lang.ClassLoader.defineClass1(Native Method)
//        at java.base/java.lang.ClassLoader.defineClass(ClassLoader.java:1016)
    }
}
