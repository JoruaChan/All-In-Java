package cn.joruachan.study.springproxy;

import java.io.Serializable;

/**
 * 房东<br>
 * 作为被代理的对象，交给代理人——中介
 *
 * @author JoruaChan
 */
public class Landlord {
    public void sell(){
        System.out.println("我是："+ this.getClass().getSimpleName() +", 开始卖房子啦～");
    }

    public void rent() {
        System.out.println("我是："+ this.getClass().getSimpleName() +", 开始租房子啦～");
    }
}
