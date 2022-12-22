package site.lgong.thread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自定义同步工具
 */
public class LiuLock {

    private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0, arg);
        }

        @Override
        protected boolean tryRelease(int arg) {
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }

    private Sync sync = new Sync();

    public void lock() {
        // 如果子类重写了父类的方法，真正执行的是子类覆盖的方法，如果子类没有覆盖父类的方法，执行的是父类的方法。
        // 父类中真正被执行的是子类的tryAcquire方法
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }
}
