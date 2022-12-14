package site.lgong.string;

/**
 * String 类型值的不可变
 */
public class StrTest {

    private static final String STR = "bbb";

    public static void main(String[] args) {

        System.out.println(System.identityHashCode(STR));
        // STR = appendStr(STR); 编译失败，因为final修饰
        String res = appendStr(STR);
        System.out.println(res);
        System.out.println(System.identityHashCode(res));

        // 打印日志：
        // 2137589296
        // bbbaaa
        // 1286783232
        // 说明地址改变了
    }

    public static String appendStr(String s) {
        s += "aaa";
        return s;
    }

}
