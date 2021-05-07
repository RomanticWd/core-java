## core-java
《Java核心技术-第11版》和《Effective Java-第2版》阅读笔记

### 20201203 泛型
#### 1. 类型参数（type parameter）
```
var files = new ArrayList<String>();
```
其中<>就是类型参数，用于指定元素的类型。

#### 2. 通配符类型（wukdcard type）
```java
public class Pair<T> {
    private T first;
    private T second;

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
}
```
其中T是类型变量，用尖括号括起来，放在类名的后面。
一个泛型类可以有多个类型变量，例如：
```
public class Pair<T,U> {...}
```
类型变量在整个类定义中用于指定方法的返回类型以及字段和局部变量的类型。

#### 3. 类型变量的限定
```
public static <T extends Comparable> T min(T[] a)...
```
现在main方法min只能在实现了Comparable接口的类的数组上调用。

* Comparable是一个接口，为什么使用extends关键字而不是implement？
表示T应该是限定类型（bounding type）的子类型（subtype）。T和限定类型可以是类，也可以是接口。
选择extends的原因是它更接近子类型的概念。

一个类型变量或通配符可以有多个限定，例如：
```
T extends Comparable & Serializable
```
限定类型用‘&’分隔，而逗号用来分隔类型变量。

#### 4. 类型擦除
* 虚拟机中没有泛型，只有普通的类和方法。
* 所有的类型参数都会替换为他们的限定类型。
* 会合成桥方法来保持多态。
* 为保持类型安全性，必要时会插入强制类型转换。

### 20201203 泛型的局限性 （P338）
#### 1. 不能用基本类型实例化类型参数
没有Pair<double>，只有Pair<Double>。
原因在于类型擦除，擦除之后，Pair类中含有object类型的字段，而Object不能存储double值。

#### 2. 运行时类型查询只适用于原始类型
```
if (a instanceof Pair<String>)   //ERROR
```
#### 3. 不能创建参数化类型的数组
```
var table = new Pair<String>[10]; //ERROR
```
table的类型是Pair[],类型擦除后变为Object[]。

#### 4. Varargs警告

#### 5. 不能实例化类型变量
不能在类似new T(...)的表达式中使用类型变量。
```
// ERROR
public Pair() {
    first = new T();
    second = new T();
}
```

#### 6. 不能构造泛型数组

#### 7. 泛型类的静态上下文中类型变量无效
不能在静态字段或方法中引用类型变量。
```java
public class Singleton<T> {
    private static T singleInstance; //ERROR
    private static T getSingleInstance() {
        if (singleInstance == null) 
        return singleInstance;
    }
}
```
如果这样可行，可以声明一个Singleton<Random>共享一个随机数生成器，另外声明一个Singleton<JFileChoose>共享一个文件选择器对话框。
类型擦除过后，只剩下Singleton，只包含一个singleInstance字段。不能区分具体他是谁的。

#### 8. 不能抛出或捕获泛型类的实例
既不能抛出也不能捕获泛型类的对象。Throwable甚至都是不合法的。

#### 9. 可以取消对检查型异常的检查