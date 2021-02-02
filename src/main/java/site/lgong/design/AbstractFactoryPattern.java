package site.lgong.design;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 抽象工厂模式：提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类
 * 这种模式有点类似于多个供应商负责提供一系列类型的产品。
 */
public class AbstractFactoryPattern {

    public static void main(String[] args) throws IOException {
        AbstractFactory fastFactory = new FastFactory();
        HtmlDocument fastHtml = fastFactory.createHtml("#Hello\nHello, world!");
        System.out.println(fastHtml.toHtml());
        fastHtml.save(Paths.get(".", "fast.html"));
        WordDocument fastWord = fastFactory.createWord("#Hello\nHello, world!");
        fastWord.save(Paths.get(".", "fast.doc"));

        AbstractFactory goodFactory = new GoodFactory();
        HtmlDocument goodHtml = goodFactory.createHtml("#Hello\nHello, world!");
        System.out.println(goodHtml.toHtml());
        goodHtml.save(Paths.get(".", "good.html"));
        WordDocument goodWord = goodFactory.createWord("#Hello\nHello, world!");
        goodWord.save(Paths.get(".", "good.doc"));
    }

}

/**
 * 抽象工厂：提供一个Markdown文本转换为HTML和Word的服务
 */
interface AbstractFactory {
    // 创建Html文档:
    HtmlDocument createHtml(String md);

    // 创建Word文档:
    WordDocument createWord(String md);
}

// 抽象产品：Html文档接口
interface HtmlDocument {
    String toHtml();

    void save(Path path) throws IOException;
}

// 抽象产品：Word文档接口
interface WordDocument {
    void save(Path path) throws IOException;
}

/**
 * 供应商：Fast  提供的Html转换服务
 */
class FastHtmlDocument implements HtmlDocument {

    private String md;

    public FastHtmlDocument(String md) {
        this.md = md;
    }

    public String toHtml() {
        // reduce:第一次执行时，accumulator函数的第一个参数为identity，而第二个参数为流中的第一个元素。
        //        第二次执行时，第一个参数为第一次函数执行的结果，第二个参数为流中的第三个元素
        return md.lines().map(s -> {
            if (s.startsWith("#")) {
                return "<h1>" + s.substring(1) + "</h1>";
            }
            return "<p>" + s + "</p>";
        }).reduce("", (acc, s) -> acc + s + "\n");
    }

    public void save(Path path) throws IOException {
        Files.write(path, toHtml().getBytes("UTF-8"));
    }
}

/**
 * 供应商：Fast  提供的Word转换服务
 */
class FastWordDocument implements WordDocument {

    private String md;

    public FastWordDocument(String md) {
        this.md = md;
    }

    public void save(Path path) throws IOException {
        String doc = "{\\rtf1\\ansi\n{\\fonttbl\\f0\\fswiss\\fcharset0 Helvetica-Bold;\\f1\\fswiss\\fcharset0 Helvetica;}\n";
        String body = md.lines().map(s -> {
            if (s.startsWith("#")) {
                return String.format("\\f0\\b\\fs24 \\cf0%s\\par\n", s.substring(1));
            }
            return String.format("\\f1\\b0%s\\par\n", s);
        }).reduce("", (acc, s) -> acc + s);
        String content = doc + body + "}";
        Files.write(path, content.getBytes("UTF-8"));
    }
}

/**
 * 供应商：Fast  用来提供两种服务的工厂
 */
class FastFactory implements AbstractFactory {
    public HtmlDocument createHtml(String md) {
        return new FastHtmlDocument(md);
    }

    public WordDocument createWord(String md) {
        return new FastWordDocument(md);
    }
}

/**
 * 供应商：Good  提供的WORD转换服务
 */
class GoodWordDocument implements WordDocument {

    private String md;

    public GoodWordDocument(String md) {
        this.md = md;
    }

    @Override
    public void save(Path path) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<?mso-application progid=\"Word.Document\"?>\n"
                + "<w:wordDocument xmlns:aml=\"http://schemas.microsoft.com/aml/2001/core\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"http://schemas.microsoft.com/office/word/2003/wordml\" xmlns:wx=\"http://schemas.microsoft.com/office/word/2003/auxHint\" xmlns:wsp=\"http://schemas.microsoft.com/office/word/2003/wordml/sp2\" w:macrosPresent=\"no\" w:embeddedObjPresent=\"no\" w:ocxPresent=\"no\" xml:space=\"preserve\">\n"
                + "<w:fonts><w:font w:name=\"Times New Roman\"><w:panose-1 w:val=\"02020603050405020304\"/><w:charset w:val=\"00\"/><w:family w:val=\"Roman\"/><w:pitch w:val=\"variable\"/><w:sig w:usb-0=\"E0002EFF\" w:usb-1=\"C000785B\" w:usb-2=\"00000009\" w:usb-3=\"00000000\" w:csb-0=\"000001FF\" w:csb-1=\"00000000\"/></w:font></w:fonts>\n"
                + "<w:styles><w:style w:type=\"paragraph\" w:default=\"on\" w:styleId=\"a\"><w:name w:val=\"Normal\"/><w:rPr><w:kern w:val=\"2\"/><w:sz w:val=\"21\"/><w:sz-cs w:val=\"22\"/></w:rPr></w:style><w:style w:type=\"paragraph\" w:styleId=\"1\"><w:name w:val=\"heading 1\"/><w:basedOn w:val=\"a\"/><w:rsid w:val=\"00DC0742\"/><w:rPr><w:b/><w:b-cs/><w:kern w:val=\"44\"/><w:sz w:val=\"44\"/><w:sz-cs w:val=\"44\"/></w:rPr></w:style></w:styles>\n"
                + "<w:docPr><wsp:rsids><wsp:rsidRoot wsp:val=\"00DC0742\"/><wsp:rsid wsp:val=\"0003739C\"/><wsp:rsid wsp:val=\"00DC0742\"/><wsp:rsid wsp:val=\"00EB4B25\"/></wsp:rsids></w:docPr>\n"
                + "<w:body><wx:sect><wx:sub-section>");
        md.lines().forEach(s -> {
            if (s.startsWith("#")) {
                sb.append(String.format(
                        "<w:p wsp:rsidR=\"0003739C\" wsp:rsidRDefault=\"00DC0742\" wsp:rsidP=\"00DC0742\"><w:pPr><w:pStyle w:val=\"1\"/></w:pPr><w:r><w:t>%s</w:t></w:r></w:p>",
                        s.substring(1)));
            } else {
                sb.append(String.format(
                        "<w:p wsp:rsidR=\"00DC0742\" wsp:rsidRDefault=\"00DC0742\"><w:r><w:t>%s</w:t></w:r></w:p>", s));
            }
        });
        sb.append("</wx:sub-section></wx:sect></w:body></w:wordDocument>");
        String content = sb.toString();
        Files.writeString(path, content);
    }
}

/**
 * 供应商：Good  提供的HTML转换服务
 */
class GoodHtmlDocument implements HtmlDocument {

    private String md;

    public GoodHtmlDocument(String md) {
        this.md = md;
    }

    @Override
    public String toHtml() {
        String body = md.lines().map(s -> {
            if (s.startsWith("#")) {
                return "<h1>" + s.substring(1) + "</h1>";
            }
            return "<p>" + s + "</p>";
        }).reduce("", (acc, s) -> acc + s + "\n");
        return "<html>\n<body>\n" + body + "\n</body>\n</html>";
    }

    @Override
    public void save(Path path) throws IOException {
        Files.write(path, toHtml().getBytes("UTF-8"));
    }
}

/**
 * 供应商：Good  用来提供两种服务的工厂
 */
class GoodFactory implements AbstractFactory {
    public HtmlDocument createHtml(String md) {
        return new GoodHtmlDocument(md);
    }

    public WordDocument createWord(String md) {
        return new GoodWordDocument(md);
    }
}