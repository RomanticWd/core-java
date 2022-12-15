package site.lgong.jvm;

/**
 * 被动引用：5种会触发类初始化的行为叫主动引用，除这五种以外的引用类的方式都不会触发初始化。
 */
public class NotInitialization {

    public static void main(String[] args) {
//        例子一： 通过子类引用父类的静态字段，不会导致子类初始化，对应静态字段，只有直接定义这个字段的类才会被初始化
//        System.out.println(SubClass.value);
        // 输出结果：
//        SuperClass init
//        123


        //        例子二： 通过数组定义来引用类，不会触发此类的初始化
        SuperClass[] sca = new SuperClass[10];
        // 输出结果： 空
        // 说明不会触发superClass的初始化


        //        例子三： 常量在编译阶段会存入调用类的常量池中，本质上没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化
        System.out.println(ConstClass.HELLOWORLD);
        // 输出结果： hello world
        // 说明不会触发ConstClass的初始化, 在编译阶段通过常量传播优化，已经将此常量的值“hello world” 存储到了NotInitialization类的常量池中
    }
}
