package site.lgong.design;

import java.math.BigDecimal;

/**
 * 静态工厂模式：定义一个用于创建对象的接口，让子类决定实例化哪一个类。Factory Method使一个类的实例化延迟到其子类。
 */
public class FactoryPattern {

    public static void main(String[] args) {
        // 调用方可以完全忽略真正的工厂NumberFactoryImpl和实际的产品BigDecimal，这样做的好处是允许创建产品的代码独立地变换，而不会影响到调用方
        NumberFactory numberFactory = NumberFactory.getFactory();
        numberFactory.parse("123.456");
    }
}

/**
 * 解析字符串到Number的Factory
 */
interface NumberFactory {
    Number parse(String s);

    // 获取工厂实例:
    static NumberFactory getFactory() {
        return impl;
    }

    NumberFactory impl = new NumberFactoryImpl();
}

/**
 * 工厂的实现类
 */
class NumberFactoryImpl implements NumberFactory {
    public Number parse(String s) {
        return new BigDecimal(s);
    }
}





