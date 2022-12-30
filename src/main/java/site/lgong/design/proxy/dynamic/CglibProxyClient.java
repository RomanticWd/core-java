package site.lgong.design.proxy.dynamic;

import net.sf.cglib.proxy.Enhancer;
import site.lgong.design.proxy.statics.XiaoMing;

/**
 * JDK 动态代理有一个最致命的问题是其只能代理实现了接口的类, 为了解决这个问题，我们可以用 CGLIB 动态代理机制来避免。
 * <p>
 * Spring 中的 AOP 模块中：如果目标对象实现了接口，则默认采用 JDK 动态代理，否则采用 CGLIB 动态代理。
 * <p>
 * CGLIB 动态代理是通过生成一个被代理类的子类来拦截被代理类的方法调用，因此不能代理声明为 final 类型的类和方法。
 */
public class CglibProxyClient {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();

        // 构造一个小红
        IWork xiaoHong = new XiaoHong();
        Class<? extends IWork> aClass = xiaoHong.getClass();
        ClassLoader classLoader = aClass.getClassLoader();
        enhancer.setSuperclass(aClass);
        enhancer.setClassLoader(classLoader);
        enhancer.setCallback(new DebugMethodInterceptor());
        IWork workProxy = (IWork) enhancer.create();
        workProxy.seekWork();

        // 同样的，小明找房子也可以通过动态代理实现
        XiaoMing xiaoMing = new XiaoMing();
        enhancer.setSuperclass(xiaoMing.getClass());
        enhancer.setClassLoader(xiaoMing.getClass().getClassLoader());
        enhancer.setCallback(new DebugMethodInterceptor());
        // cglib动态代理类
        XiaoMing roomProxy = (XiaoMing) enhancer.create();
        roomProxy.seekRoom();
        // final方法，没有起到代理增强的效果，没有打印代理类中的DebugMethodInterceptor before和after信息
        roomProxy.seekGirlFriend();
    }
}
