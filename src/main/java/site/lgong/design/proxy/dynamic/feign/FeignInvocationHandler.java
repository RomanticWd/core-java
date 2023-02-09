package site.lgong.design.proxy.dynamic.feign;

import site.lgong.util.CheckUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * feign的动态代理类
 */
public class FeignInvocationHandler implements InvocationHandler {

    private Map<Method, InvocationHandlerFactory.MethodHandler> dispatch;

    /**
     * 构造函数，传入dispatch，存放指定方法和代理实际执行逻辑的map
     */
    public FeignInvocationHandler(Map<Method, InvocationHandlerFactory.MethodHandler> dispatch) {
        this.dispatch = CheckUtils.checkNotNull(dispatch, "dispatch for %s", dispatch);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这里方法名的invoke和方法内部的invoke容易混淆，方法名的invoke是动态代理实现接口里的（不能修改名称），方法内部的invoke是接口内部定义的方法（方法名称可以修改）
        return dispatch.get(method).invoke(args);
    }
}
