package site.lgong.service;

import site.lgong.generic.GenericB;

/**
 * 抽象类-测试类
 */
public class AbstractTest {

    public static void main(String[] args) {

        Integer[] params = new Integer[]{5, 6, 7, 1, 10};

        AbstractClient<String, Integer> abstractClient = new AbstractClient<String, Integer>() {
            protected <ResT extends Comparable> void methodA(ResT res) {
                System.out.println("A res:" + res);
            }

            protected <ResT extends Comparable> Integer methodB(ResT res) {
                return res.compareTo(compareNum);
            }
        };

        GenericB<Integer> genericB = abstractClient.processB(params);
        System.out.println(genericB.getFirst());
        System.out.println(genericB.getSecond());
    }

}
