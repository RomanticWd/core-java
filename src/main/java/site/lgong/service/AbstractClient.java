package site.lgong.service;

import site.lgong.generic.GenericB;

/**
 * 抽象类-客户端
 */
public abstract class AbstractClient<ResT, RepT> {

    protected static final int compareNum = 5;

    /**
     * 方法A
     *
     * @param res 泛型ResT
     */
    protected abstract <ResT extends Comparable> void methodA(ResT res);

    /**
     * 方法B
     *
     * @param res 泛型ResT
     * @return 泛型RepT
     */
    protected abstract <ResT extends Comparable> RepT methodB(ResT res);

    public <ResT extends Comparable> GenericB<ResT> processB(ResT[] params) {
        if (null == params || params.length == 0) return null;

        ResT min = params[0];
        ResT max = params[0];

        for (ResT param : params) {
            // 如果methodA没有进行<ResT extends Comparable>限制的话，会报错
            // 因为抽象类可能存在多个实现，每个实现的ResT都会不同，如果不加限制，会存在问题
            methodA(param);
            System.out.println("比较结果：" + methodB(param));
            if (min.compareTo(param) < 0) max = param;
            if (min.compareTo(param) > 0) min = param;
        }
        return new GenericB<ResT>(min, max);
    }
}
