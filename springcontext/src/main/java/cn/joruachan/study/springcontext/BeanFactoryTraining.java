package cn.joruachan.study.springcontext;

import cn.joruachan.study.springcontext.bean.Service;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Spring的BeanFactory实践<br>
 *
 * @author JoruaChan
 */
public class BeanFactoryTraining {

    private DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    public void resolve(String xmlPath) {
        Resource resource = new ClassPathResource(xmlPath);

        BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
    }

    public void tryService() {
        Service service = beanFactory.getBean(Service.class);
        service.service();
    }

    public static void main(String[] args) {
        BeanFactoryTraining training = new BeanFactoryTraining();
        training.resolve("beanfactory.xml");

        // DefaultListableBeanFactory未处理依赖关系，Autowired未生效？
        training.tryService();
    }
}
