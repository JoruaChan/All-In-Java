package cn.joruachan.study.springboot;

import org.springframework.core.io.DefaultResourceLoader;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class MyResourceLoader extends DefaultResourceLoader {

    public MyResourceLoader() {
        super();

        addProtocolResolver(new MyProtocolResolver());
    }

    public MyResourceLoader(ClassLoader classLoader) {
        super(classLoader);

        addProtocolResolver(new MyProtocolResolver());
    }
}
