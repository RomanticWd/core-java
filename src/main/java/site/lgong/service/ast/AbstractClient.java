package site.lgong.service.ast;

import site.lgong.entity.Student;

import java.util.function.Function;

/**
 * 抽象类-客户端
 */
public abstract class AbstractClient<ResT, ResT2, RepT> {

    protected static final int compareNum = 5;

    /**
     * 方法A
     *
     * @param res 泛型ResT
     */
    protected abstract void methodA(ResT res);

    /**
     * 方法B
     *
     * @param res 泛型ResT
     * @return 泛型RepT
     */
    protected abstract RepT methodB(ResT2 res);

    /**
     * 方法C
     *
     * @param res 泛型ResT
     */
    protected abstract void methodC(ResT res);


    public void processA(ResT params) {
        methodA(params);
        methodC(params);
    }

    public void processB(ResT2 params, Function<ResT2, ResT> function) {
        RepT repT = methodB(params);
        ResT apply = function.apply(params);
        methodC(apply);
    }

    public static void main(String[] args) {


        String params = "张";
        Student student = new Student();
        student.setAge(12);
        student.setName("李");

        AbstractClient<String, Student, Integer> abstractClient = new AbstractClient<>() {
            @Override
            protected void methodA(String res) {
                System.out.println("处理A:" + res);
            }

            @Override
            protected Integer methodB(Student res) {
                System.out.println("处理B:" + res.getName());
                return res.getAge();
            }

            @Override
            protected void methodC(String res) {
                System.out.println("处理C:" + res);
            }
        };

        abstractClient.processA(params);
        abstractClient.processB(student, new Function<Student, String>() {
            @Override
            public String apply(Student student) {
                return student.getName();
            }
        });
    }
}
