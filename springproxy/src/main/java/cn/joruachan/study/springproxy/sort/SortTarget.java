package cn.joruachan.study.springproxy.sort;

import org.springframework.stereotype.Component;

/**
 * 排序测试类的目标对象
 *
 * @author JoruaChan
 */
@Component
public class SortTarget {

    public void test1() {
        System.out.println("This method is test1");
    }

    public void test2() {
        System.out.println("This method is test2");
    }
}
