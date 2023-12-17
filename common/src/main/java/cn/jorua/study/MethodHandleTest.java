package cn.jorua.study;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class MethodHandleTest {

    static class ClassA {
        public void println(String s) {
            System.out.println("ClassA:" + s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object object = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
        getPrintlnMethodHandle(object).invokeExact("Wo ni ma!!!");
    }

    private static MethodHandle getPrintlnMethodHandle(Object receiver) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = MethodType.methodType(void.class, String.class);
        return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", methodType).bindTo(receiver);
    }

}
