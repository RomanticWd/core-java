package site.lgong.design.proxy.dynamic.feign;

/**
 * 默认的动态代理实际执行方法
 */
public class DefaultMethodHandler implements InvocationHandlerFactory.MethodHandler {
    @Override
    public Object invoke(Object[] argv) throws Throwable {
        // 在feign中，会在此处封装负载均衡和urlConnection调用， 详见feign.SynchronousMethodHandler#invoke
        // 参考https://www.jianshu.com/p/9203f6aa80ba
        System.out.println("DefaultMethodHandler invoke");
        return null;
    }
}
