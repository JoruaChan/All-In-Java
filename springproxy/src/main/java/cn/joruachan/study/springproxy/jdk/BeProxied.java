package cn.joruachan.study.springproxy.jdk;

/**
 * 接口代理
 *
 * @author JoruaChan
 */
public class BeProxied implements IProxy {
    @Override
    public void execute() {
        System.out.println("BeProxied#execute");
    }

    /**
     * 只要实现了IProxy接口，这个方法在Spring中是永远不会被执行到的！
     * 因为生成的代理类不会代理这个方法！！！
     * 就算是指定拦截这个方法，由于不会生成该代理方法，所以依旧执行不到！！
     */
    public void other() {
        System.out.println("BeProxied#other");
    }
}
