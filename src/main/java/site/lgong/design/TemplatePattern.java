package site.lgong.design;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板方法模式：定义一个操作的一系列步骤，对于某些暂时确定不下来的步骤，就留给子类去实现好了，这样不同的子类就可以定义出不同的步骤。
 * 模板方法的核心在于定义一个“骨架”
 */
public class TemplatePattern {

    public static void main(String[] args) {

        // 从本地获取缓存
        AbstractSetting localSetting = new LocalSetting();
        System.out.println("test = " + localSetting.getSetting("test"));

        // 从redis获取缓存
        AbstractSetting redisSetting = new RedisSetting();
        System.out.println("test = " + redisSetting.getSetting("test"));

    }

}

abstract class AbstractSetting {
    /**
     * 骨架方法：根据key获取配置，这里的获取配置定义了下一次读取同样的key就不必再访问数据库的流程
     * 但是具体实现缓存的方式还没确定，可以用redis实现，也可以通过map实现。
     * <p>
     * 为了防止子类重写父类的骨架方法，在父类中对骨架方法使用final
     *
     * @param key 配置key
     * @return 配置信息
     */
    public final String getSetting(String key) {

        String value = lookupCache(key);
        if (value == null) {
            System.out.println("[DEBUG] load from db: " + key + " = " + value);
            value = readFromDatabase(key);
            putIntoCache(key, value);
        } else {
            System.out.println("[DEBUG] load from cache: " + key + " = " + value);
        }
        return value;
    }

    /**
     * 从缓存读取配置
     *
     * @param key
     * @return
     */
    protected abstract String lookupCache(String key);

    /**
     * 将配置放入缓存
     *
     * @param key
     * @param value
     */
    protected abstract void putIntoCache(String key, String value);

    /**
     * 从数据库获取配置
     *
     * @param key
     * @return
     */
    private String readFromDatabase(String key) {
        // 从数据库读取
        return "";
    }
}

/**
 * 用map实现的缓存
 */
class LocalSetting extends AbstractSetting {
    private Map<String, String> cache = new HashMap<>();

    protected String lookupCache(String key) {
        return cache.get(key);
    }

    protected void putIntoCache(String key, String value) {
        cache.put(key, value);
    }
}

/**
 * 用redis实现的缓存
 */
class RedisSetting extends AbstractSetting {
    private RedisClient client = RedisClient.create("redis://localhost:6379");

    protected String lookupCache(String key) {
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisCommands<String, String> commands = connection.sync();
            return commands.get(key);
        }
    }

    protected void putIntoCache(String key, String value) {
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisCommands<String, String> commands = connection.sync();
            commands.set(key, value);
        }
    }
}