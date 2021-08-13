package site.lgong.design.proxy.statics;

/**
 * 租房代理：房产中间人
 *
 * @author yue.liu
 * @since [2021/8/13 9:42]
 */
public class RoomProxy implements IRoom {

    private IRoom mRoom;//持有一个被代理人（小明）的引用

    public RoomProxy(IRoom mRoom) {
        this.mRoom = mRoom;
    }

    @Override
    public void seekRoom() {
        mRoom.seekRoom();
    }
}
