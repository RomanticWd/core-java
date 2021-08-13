package site.lgong.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 观察者模式：发布-订阅模式（Publish-Subscribe：Pub/Sub）。它是一种通知机制，让发送通知的一方（被观察方）和接收通知的一方（观察者）能彼此分离，互不影响。
 * 定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 * Spring 事件驱动模型就是观察者模式很经典的一个应用。
 */
public class ObserverPattern {

    /**
     * 优点
     * <p>
     * 降低了目标与观察者之间的耦合关系，两者之间是抽象耦合关系
     * 目标与观察者之间建立了一套触发机制
     * 支持广播通信
     * 符合“开闭原则”的要求
     * <p>
     * 缺点
     * <p>
     * 目标与观察者之间的依赖关系并没有完全解除，而且有可能出现循环引用
     * 当观察者对象很多时，通知的发布会花费很多时间，影响程序的效率
     **/

    public static void main(String[] args) {

        // observer:
        Admin a = new Admin();
        Customer c = new Customer();
        // store:
        Store store = new Store();
        // register:
        store.addObserver(a);
        store.addObserver(c);

        // 注册匿名观察者:
        store.addObserver(new ProductObserver() {
            @Override
            public void onPublished(Product product) {
                System.out.println("[Log] on product published: " + product);
            }

            @Override
            public void onPriceChanged(Product product) {
                System.out.println("[Log] on product price changed: " + product);
            }
        });

        // operation:
        store.addProduct("Design Patterns", 35.6);
        store.addProduct("Effective Java", 50.8);
        store.setProductPrice("Design Patterns", 31.9);

    }

}

/**
 * 定义一个电商网站，有多种Product（商品），同时，Customer（消费者）和Admin（管理员）对商品上架、价格改变都感兴趣，希望能第一时间获得通知
 */
class Store {

    private Map<String, Product> products = new HashMap<>();
    private List<ProductObserver> observers = new ArrayList<>();

    // 注册观察者
    public void addObserver(ProductObserver observer) {
        observers.add(observer);
    }

    // 取消注册
    public void removeObserver(ProductObserver observer) {
        observers.remove(observer);
    }

    /**
     * 商品上架
     *
     * @param name
     * @param price
     */
    public void addProduct(String name, double price) {
        Product product = new Product(name, price);
        products.put(product.getName(), product);
        // 通知所有的观察者商品上架

        // 同步通知
//        observers.forEach(observer -> observer.onPriceChanged(product));

        // 异步通知
        observers.forEach(observer -> new Thread(() -> observer.onPublished(product)).start());
    }

    /**
     * 价格变动
     *
     * @param name
     * @param price
     */
    public void setProductPrice(String name, double price) {
        Product product = products.get(name);
        if (product != null) {
            product.setPrice(price);
            // 通知所有的观察者价格变动

            // 同步通知
//            observers.forEach(observer -> observer.onPriceChanged(product));

            // 异步通知
            observers.forEach(observer -> new Thread(() -> observer.onPriceChanged(product)).start());
        }
    }

}

interface ProductObserver {

    /**
     * 商品上架
     *
     * @param product
     */
    void onPublished(Product product);

    /**
     * 商品价格改动
     *
     * @param product
     */
    void onPriceChanged(Product product);

}

/**
 * 观察者-消费者
 */
class Customer implements ProductObserver {

    @Override
    public void onPublished(Product product) {
        System.out.println("[Customer] on product published: " + product);
    }

    @Override
    public void onPriceChanged(Product product) {
        System.out.println("[Customer] on product price changed: " + product);
    }
}

/**
 * 观察者-管理员
 */
class Admin implements ProductObserver {

    @Override
    public void onPublished(Product product) {
        System.out.println("[Admin] on product published: " + product);
    }

    @Override
    public void onPriceChanged(Product product) {
        System.out.println("[Admin] on product price changed: " + product);
    }
}

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("{Product: name=%s, price=%s}", name, price);
    }
}
