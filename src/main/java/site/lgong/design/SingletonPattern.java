package site.lgong.design;

/**
 * 单例模式：保证一个类仅有一个实例，并提供一个访问它的全局访问点。
 * 目的是为了保证在一个进程中，某个类有且仅有一个实例。
 */
public class SingletonPattern {

    // 因为这个类只有一个实例，因此，自然不能让调用方使用new Xyz()来创建实例了。所以，单例的构造方法必须是private，这样就防止了调用方自己创建实例
    // 在类的内部，是可以用一个静态字段来引用唯一创建的实例

    public static void main(String[] args) {
        // 枚举类实现单例模式的调用方式
        String name = World.INSTANCE.getName();

        // 通过静态方法实现单例模式
        Singleton instance = Singleton.getInstance();
    }
}

/**
 * 单例模式的实现方式很简单：
 * <p>
 * 只有private构造方法，确保外部无法实例化；
 * 通过private static变量持有唯一实例，保证全局唯一性；
 * 通过public static方法返回此唯一实例，使外部调用方能获取到实例。
 */
class Singleton {
    // 静态字段引用唯一实例:
    private static final Singleton INSTANCE = new Singleton();
    // public static final Singleton INSTANCE = new Singleton();

    // private构造方法保证外部无法实例化:
    private Singleton() {
    }

    // 通过静态方法返回实例:
    public static Singleton getInstance() {
        return INSTANCE;
    }
}

class LazySingleton {
    // 静态字段引用唯一实例:
    private static LazySingleton INSTANCE = null;

    // private构造方法保证外部无法实例化:
    private LazySingleton() {
    }

    // 这种写法在多线程中是错误的，在竞争条件下会创建出多个实例。必须对整个方法进行加锁
    public static LazySingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LazySingleton();
        }
        return INSTANCE;
    }

    // 加锁会严重影响并发性能
    public synchronized static LazySingleton getLockInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LazySingleton();
        }
        return INSTANCE;
    }

    // 双重检查的方式实现单例模式
    // 由于Java的内存模型，双重检查在这里不成立。要真正实现延迟加载，只能通过Java的ClassLoader机制完成
    // 如果没有特殊的需求，使用Singleton模式的时候，最好不要延迟加载，这样会使代码更简单
    public static LazySingleton getDoubleCheckInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }
}

/**
 * 通过枚举实现单例模式
 * Java保证枚举类的每个枚举都是单例，所以我们只需要编写一个只有一个枚举的类即可
 */
enum World {
    // 唯一枚举:
    INSTANCE;

    private String name = "world";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
