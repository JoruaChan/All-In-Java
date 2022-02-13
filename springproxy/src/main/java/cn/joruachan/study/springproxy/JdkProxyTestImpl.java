package cn.joruachan.study.springproxy;

/**
 * Jdk代理的测试类
 *
 * @author JoruaChan
 */
public class JdkProxyTestImpl implements IJdkProxyTest {
    @Override
    public void test() {
        System.out.println("ITest实现类");
    }
}
