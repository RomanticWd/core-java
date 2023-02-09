package site.lgong.design.proxy.dynamic.feign;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 代理工厂，feign源码解析
 */
public interface InvocationHandlerFactory {

    /**
     * 内部接口，动态代理实际执行方法
     */
    interface MethodHandler {
        Object invoke(Object[] argv) throws Throwable;
    }

    /**
     * 创建代理
     *
     * @param dispatch
     * @return
     */
    InvocationHandler create(Map<Method, MethodHandler> dispatch);

    /**
     * 内部类，实现create代理
     */
    static final class Default implements InvocationHandlerFactory {

        @Override
        public InvocationHandler create(Map<Method, MethodHandler> dispatch) {
            return new FeignInvocationHandler(dispatch);
        }
    }

}
