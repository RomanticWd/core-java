package site.lgong.wrapper;

/**
 * 自动装箱规范要求Boolean、byte、char<= 127, 介于-128和127之间的short和int被包装到固定的对象中。
 * <p>
 * 装箱和拆箱是编译器要做的工作，不是虚拟机。
 * <p>
 * 使用数值包装器还有一个原因：将某些基本方法放在包装器中，这会很方便，例如将一个数字字符串转成数值，调用Integer.parseInt().
 * 这个与integer对象本身没有任务关系。是一个静态方法，Integer只是放置这方法的好地方。
 */
public class IntegerTest {

    public static void main(String[] args) {

        // Java 在编译的时候会直接将代码优化为 Integer i1=Integer.valueOf(127);，从而使用常量池中的对象。
        Integer i1 = 127;

        // 会从缓存数组中获取范围为[-128, 127]的值，如果没有就创建一个新的对象
        Integer i2 = Integer.valueOf(127);

        // 返回值是 int数字，在[-128, 127]范围的会从常量池中获取
        Integer i3 = Integer.parseInt("127");

        // 创建一个新的对象。
        Integer i4 = new Integer(127);

        System.out.println(i1 == i2);
        // true
        System.out.println(i1 == i3);
        // true
        System.out.println(i2 == i3);
        // true

        // 因为i4是新的对象 所以不会相同
        System.out.println(i4 == i1);
        // false
        System.out.println(i4 == i2);
        // false
        System.out.println(i4 == i3);
        // false

        Integer in1 = 128;
        Integer in2 = Integer.valueOf(128);
        Integer in3 = Integer.parseInt("128");

        Integer in4 = new Integer(128);

        System.out.println(in1 == in2);
        // false
        System.out.println(in1 == in3);
        // false
        System.out.println(in2 == in3);
        // false

        System.out.println(in4 == in1);
        // false
        System.out.println(in4 == in2);
        // false
        System.out.println(in4 == in3);
        // false

        // java的方法总是按值传递，不可能编写一个能够增加整形参数值的java方法
        int x1 = 3;
        triple(x1);
        System.out.println(x1);

        Integer x2 = 3;
        triple(x2);
        System.out.println(x2);
    }

    public static void triple(int x) {
        x = x * 3;
    }

    public static void triple(Integer x) {
        x = x * 3;
    }
}
