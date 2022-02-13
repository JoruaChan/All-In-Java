package cn.joruachan.study.jdkproxy;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * MethodHandle句柄的工具类<br>
 * <p>
 * 功能:
 * <ul>
 *     <li>{@linkplain MethodHandleUtil#methodHandleForMethod}, 通过 {@linkplain java.lang.reflect.Method} 获取MethodHandle</li>
 *     <li>{@linkplain MethodHandleUtil#methodHandleForMethod}, 通过 {@linkplain java.lang.invoke.MethodType}等 获取MethodHandle</li>
 * </ul>
 *
 * @author JoruaChan
 */
public class MethodHandleUtil {

    public static Constructor<MethodHandles.Lookup> LOOKUP_CONSTRUCTOR;

    private static final int ALLOWED_MODES = MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
            | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC;

    static {
        Class<MethodHandles.Lookup> aClass = MethodHandles.Lookup.class;
        try {
            Constructor constructor = aClass.getDeclaredConstructor(Class.class, int.class);
            constructor.setAccessible(true);

            LOOKUP_CONSTRUCTOR = constructor;
        } catch (Exception e) {
            throw new Error("初始化失败！");
        }
    }

    /**
     * 通过{@linkplain MethodHandles#lookup()}可能存在权限问题，需要通过反射的方式获取LookUp对象
     *
     * @return
     */
    private static final MethodHandles.Lookup getLookUp(Class lookUpClass)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return LOOKUP_CONSTRUCTOR.newInstance(lookUpClass, ALLOWED_MODES);
    }

    /**
     * 获取指定方法对应的MethodHandle句柄对象
     *
     * @param method
     * @return
     */
    public static final MethodHandle methodHandleForMethod(Method method) {
        // 获取调用该方法的类
        Class<?> callerClass = method.getDeclaringClass();
        try {
            // 获取该类，该方法的方法句柄
            return getLookUp(callerClass).unreflectSpecial(method, callerClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过类、方法名、MethodType获取方法句柄
     *
     * @param clazz      方法所在类
     * @param methodName 方法名
     * @param methodType 返参、传参
     * @return
     */
    public static final MethodHandle methodHandleForMethod(Class<?> clazz, String methodName, MethodType methodType) {
        try {
            return MethodHandles.lookup().findVirtual(clazz, methodName, methodType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /************** 通过MethodType找句柄 *************/
//        MethodType methodType = MethodType.methodType(int.class);
//
//        // String#length 的句柄
//        MethodHandle stringLengthMethodHandle = MethodHandles.lookup()
//                .findVirtual(String.class, "length", methodType);
//        System.out.println(stringLengthMethodHandle);
//
//        // Integer#intValue 的句柄
//        MethodHandle intMethodHandle = MethodHandles.lookup()
//                .findVirtual(Integer.class, "intValue", methodType);
//        System.out.println(intMethodHandle);
        /******************** end ********************/
    }
}
