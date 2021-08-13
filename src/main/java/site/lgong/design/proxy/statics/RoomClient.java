package site.lgong.design.proxy.statics;

/**
 * 租房客户端
 *
 * @author yue.liu
 * @since [2021/8/13 9:43]
 */
public class RoomClient {

    /**
     * 静态代理缺点：只能为给定接口下的实现类做代理，如果接口不同就需要定义不同的代理类
     * 比如小明想要买房，此时的RoomProxy就满足不了需求，得重新定义代理类，代理买房接口
     * 随着系统的复杂度增加，就会很难维护这么多代理类和被代理类之间的关系
     * <p>
     * 与动态代理区别：
     * 静态代理在我们的代码运行之前，代理类的.class文件就已经存在，例如上述的RoomAgency.java，在经过javac编译之后，就会变成RoomAgency.class；
     * 而动态代理则与静态代理相反，在代码运行之前不存在代理类的.class文件，在代码运行时才动态的生成代理类。
     **/
    public static void main(String[] args) {

        //小明想租房
        XiaoMing xiaoMing = new XiaoMing();

        //找一个代理人，房产中介
        RoomProxy proxy = new RoomProxy(xiaoMing);

        //房产中介帮忙找房
        proxy.seekRoom();

    }

}
