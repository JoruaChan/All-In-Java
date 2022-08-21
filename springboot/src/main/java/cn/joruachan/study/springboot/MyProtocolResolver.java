package cn.joruachan.study.springboot;

import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class MyProtocolResolver implements ProtocolResolver {

    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {
        System.out.println("=========================");
        return null;
    }
}
