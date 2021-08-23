package site.lgong.design;

import java.util.ArrayList;
import java.util.List;

/**
 * 责任链模式：多个对象都有机会处理请求，从而避免请求的发送者和接收者之间的耦合关系。将这些对象连成一条链，并沿着这条链传递该请求，直到有一个对象处理它为止。
 * 责任链模式把多个处理器串成链，然后让请求在链上传递
 *
 * @author yue.liu
 * @since [2021/8/23 15:55]
 */
public class ChainOfResponsibilityPattern {

    public static void main(String[] args) {
        String msg = "大家好 ";//以下三行模拟一个请求
        Request request = new Request();
        request.setMsg(msg);

        Response response = new Response();//响应

        FilterChain fc = new FilterChain();//过滤器链
        HttpFilter f1 = new HttpFilter();//创建过滤器
        SensitiveFilter f2 = new SensitiveFilter();//创建过滤器

        fc.addFilter(f1);//把过滤器添加到过滤器链中
        fc.addFilter(f2);
        fc.doFilter(request, response, fc);//直接调用过滤器链的doFilter()方法进行处理

    }

}

/**
 * 请求
 *
 * @author yue.liu
 * @since 2021/8/23 16:05
 **/
class Request {
    String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}

/**
 * 响应
 *
 * @author yue.liu
 * @since 2021/8/23 16:05
 **/
class Response {
    public void deal() {
        System.out.println();
    }
}

/**
 * 过滤接口
 *
 * @author yue.liu
 * @since 2021/8/23 16:08
 **/
interface Filter {
    void doFilter(Request req, Response rep, Filter filter);
}

/**
 * http校验的实现
 *
 * @author yue.liu
 * @since 2021/8/23 16:10
 **/
class HttpFilter implements Filter {
    @Override
    public void doFilter(Request req, Response rep, Filter filter) {
        System.out.println("处理了http验证, " + req.getMsg());
        filter.doFilter(req, rep, filter);
    }
}

/**
 * 敏感词校验的实现
 *
 * @author yue.liu
 * @since 2021/8/23 16:10
 **/
class SensitiveFilter implements Filter {
    @Override
    public void doFilter(Request req, Response rep, Filter filter) {
        System.out.println("处理了敏感字符替换, " + req.getMsg());
        filter.doFilter(req, rep, filter);
    }
}

/**
 * 过滤链
 *
 * @author yue.liu
 * @since 2021/8/23 16:10
 **/
class FilterChain implements Filter {

    List<Filter> filterList = new ArrayList<>();
    private int index;

    public FilterChain addFilter(Filter filter) {
        filterList.add(filter);
        return this;
    }

    @Override
    public void doFilter(Request req, Response res, Filter filter) {
        if (index == filterList.size()) {
            return;//这里是逆序处理响应的关键, 当index为容器大小时, 证明对request的处理已经完成, 下面进入对response的处理.
        }
        Filter f = filterList.get(index);//过滤器链按index的顺序拿到filter
        index++;
        f.doFilter(req, res, filter);
    }
}
