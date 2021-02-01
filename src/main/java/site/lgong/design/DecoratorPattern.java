package site.lgong.design;

/**
 * 装饰器模式：一种在运行期动态给某个对象的实例增加功能的方法
 * 动态地给一个对象添加一些额外的职责。就增加功能来说，相比生成子类更为灵活。
 */
public class DecoratorPattern {

    public static void main(String[] args) {
        TextNode n1 = new SpanNode();
        // 好处：把核心功能和附加功能给分开了。核心功能指TextNode这些真正读数据的源头
        // 附加功能指加加粗、斜体、下划线这些功能。
        // 如果我们要新增核心功能，就增加TextNode的子类，例如DivNode。
        // 如果我们要增加附加功能，就增加NodeDecorator的子类，例如UnderlineDecorator。
        // 两部分都可以独立地扩展，而具体如何附加功能，由调用方自由组合，从而极大地增强了灵活性。
        TextNode n2 = new BoldDecorator(new UnderlineDecorator(new SpanNode()));

        TextNode n3 = new ItalicDecorator(new BoldDecorator(new SpanNode()));
        /**
         * 上面的写法等价于：
         * TextNode spanNode = new SpanNode();
         * TextNode boldDecorator = new BoldDecorator(spanNode);
         * TextNode italicDecorator = new ItalicDecorator(boldDecorator);
         */

        n1.setText("Hello");
        n2.setText("Decorated");
        n3.setText("World");

        // 输出<span>Hello</span>
        System.out.println(n1.getText());

        // 输出<b><u><span>Decorated</span></u></b>
        System.out.println(n2.getText());

        // 输出<i><b><span>World</span></b></i>
        System.out.println(n3.getText());
    }
}

/**
 * 顶层接口
 */
interface TextNode {
    // 设置text:
    void setText(String text);

    // 获取text:
    String getText();
}

/**
 * 核心节点，例如<span>，它需要从TextNode直接继承
 * 后续所有其他装饰类，都以它作为对象传入
 */
class SpanNode implements TextNode {
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return "<span>" + text + "</span>";
    }
}

/**
 * 节点装饰器
 * 抽象类来实现接口，接口的方法可根据需要选择是否重写
 */
abstract class NodeDecorator implements TextNode {

    // 参数为接口类型
    protected final TextNode target;

    // 构造器入参可以是接口、接口的实现类、接口实现类的子类
    protected NodeDecorator(TextNode target) {
        this.target = target;
    }

    public void setText(String text) {
        this.target.setText(text);
    }
}

/**
 * 加粗功能
 * 继承实现接口的抽象类时，子类必须重写抽象类未实现的方法。
 */
class BoldDecorator extends NodeDecorator {
    public BoldDecorator(TextNode target) {
        super(target);
    }

    public String getText() {
        return "<b>" + target.getText() + "</b>";
    }
}

/**
 * 下划线功能
 * 继承实现接口的抽象类时，子类必须重写抽象类未实现的方法。
 */
class UnderlineDecorator extends NodeDecorator {
    public UnderlineDecorator(TextNode target) {
        super(target);
    }

    public String getText() {
        return "<u>" + target.getText() + "</u>";
    }
}

/**
 * 斜体功能
 * 继承实现接口的抽象类时，子类必须重写抽象类未实现的方法。
 */
class ItalicDecorator extends NodeDecorator {
    public ItalicDecorator(TextNode target) {
        super(target);
    }

    public String getText() {
        return "<i>" + target.getText() + "</i>";
    }
}
