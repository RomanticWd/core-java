package site.lgong.design.proxy.statics;

/**
 * 租房人：小明
 *
 * @author yue.liu
 * @since [2021/8/13 9:41]
 */
public class XiaoMing implements IRoom {
    @Override
    public void seekRoom() {
        System.out.println("小明在找房子");
    }

    public final void seekGirlFriend() {
        System.out.println("小明在找女朋友");
    }
}
