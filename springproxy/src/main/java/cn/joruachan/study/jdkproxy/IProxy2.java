package cn.joruachan.study.jdkproxy;

import cn.joruachan.study.return0.SubReturn;

/**
 * 基础的代理接口
 * <p>
 * 为了和{@linkplain IProxy}做比较；
 *
 * @author JoruaChan
 */
public interface IProxy2 {
    /**
     * 被代理接口
     */
    // String byProxy();
    // Integer byProxy();
    SubReturn byProxy();
}
