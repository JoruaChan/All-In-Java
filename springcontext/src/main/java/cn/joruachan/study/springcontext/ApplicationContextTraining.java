package cn.joruachan.study.springcontext;

import cn.joruachan.study.springcontext.bean.Service;
import cn.joruachan.study.springcontext.lifecycle.MyLifecycle;
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
        applicationContext.registerShutdownHook();
    }

    public void tryService() {
        Service service = applicationContext.getBean(Service.class);
        service.service();
    }

    public void tryLifecycle() {
        MyLifecycle lifecycle = applicationContext.getBean(MyLifecycle.class);
        lifecycle.start();
        lifecycle.stop();
        lifecycle.start();
    }

    public static void main(String[] args) {
        ApplicationContextTraining training = new ApplicationContextTraining();
        training.resolve("beanfactory.xml");

        // DefaultListableBeanFactory未处理依赖关系，Autowired未生效？
        training.tryService();

//        training.tryLifecycle();
    }

}
