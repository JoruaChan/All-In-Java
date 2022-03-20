package cn.joruachan.study.springproxy.introduction;

/**
 * Mix-In 锁
 *
 * @author JoruaChan
 */
public interface Lockable {

    /**
     * 锁住
     */
    void lock();

    /**
     * 解锁
     */
    void unlock();

    /**
     * 是否被锁
     *
     * @return
     */
    boolean isLocked();

}
