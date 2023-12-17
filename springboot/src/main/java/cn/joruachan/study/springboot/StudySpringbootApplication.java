package cn.joruachan.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class StudySpringbootApplication {

    public static void main(String[] args) {
        // ServiceLoader.load(ProtocolResolver.class);
        SpringApplication.run(StudySpringbootApplication.class, args);
    }

    @Bean
    public Object newObject() {
        return new Object();
    }

    @Component
    public static class T implements ResourceLoaderAware {

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            if (resourceLoader instanceof DefaultResourceLoader) {
                ((DefaultResourceLoader) resourceLoader).addProtocolResolver(new MyProtocolResolver());
            }
        }
    }
}
