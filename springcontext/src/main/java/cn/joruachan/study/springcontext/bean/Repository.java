package cn.joruachan.study.springcontext.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储bean
 *
 * @author JoruaChan
 */
public class Repository {

    private List<Object> objects = new ArrayList<>();

    public void save(Object o) {
        objects.add(o);
    }

    public void remove(Object o) {
        objects.remove(o);
    }

}
