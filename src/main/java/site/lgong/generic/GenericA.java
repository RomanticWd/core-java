package site.lgong.generic;

/**
 * 泛型类A
 */
public class GenericA<T, U> {

    /**
     * 第一个参数：类型T
     */
    private T first;

    /**
     * 第二个参数：类型U
     */
    private U second;

    public GenericA() {
        this.first = null;
        this.second = null;
    }

    public GenericA(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public U getSecond() {
        return second;
    }

    public void setSecond(U second) {
        this.second = second;
    }
}
