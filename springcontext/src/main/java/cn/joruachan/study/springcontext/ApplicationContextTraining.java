package cn.joruachan.study.springcontext;

import cn.joruachan.study.springcontext.bean.Service;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring的ApplicationContext实践<br>
 *
 * @author JoruaChan
 */
public class ApplicationContextTraining {

    private ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();

    public void resolve(String xmlPath) {
        applicationContext.setConfigLocation(xmlPath);
        applicationContext.refresh();
    }

    public void tryService() {
        Service service = applicationContext.getBean(Service.class);
        service.service();
    }

    public static void main(String[] args) {
        ApplicationContextTraining training = new ApplicationContextTraining();
        training.resolve("beanfactory.xml");

        // DefaultListableBeanFactory未处理依赖关系，Autowired未生效？
        training.tryService();
    }

}
