package cn.joruachan.study.springproxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "cn.joruachan.study.springproxy")
public class AppConfig {

    @Bean
    public Landlord landlord(){
        return new Landlord();
    }

    @Bean
    public Agent agent(){
        return new Agent();
    }

}
