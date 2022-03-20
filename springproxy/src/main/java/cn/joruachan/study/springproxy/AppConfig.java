package cn.joruachan.study.springproxy;

import cn.joruachan.study.springproxy.introduction.MixInAdvisor;
import cn.joruachan.study.springproxy.jdk.BeProxied;
import cn.joruachan.study.springproxy.jdk.EmptyBeProxied;
import cn.joruachan.study.springproxy.jdk.InnerCall;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class AppConfig {

    @Bean(name = "emptyBeProxied")
    public EmptyBeProxied emptyBeProxied() {
        return new EmptyBeProxied();
    }

    @Bean(name = "beProxied")
    public BeProxied beProxied(){
        return new BeProxied();
    }

    @Bean(name = "innerCall")
    public InnerCall innerCall(){
        return new InnerCall();
    }
}
