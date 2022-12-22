package site.lgong.jvm;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器与instanceof关键字演示
 * 即使两个类源于同一个class文件，被同一个虚拟机加载，只要加载他们的类加载器不同，两个类也不相等。
 */
public class ClassLoadTest {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader.loadClass("site.lgong.jvm.ClassLoadTest").newInstance();

        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoadTest);

        // 执行结果：
//        class site.lgong.jvm.ClassLoadTest
//        false
    }
}
