package cn.joruachan.study.proxy;

/**
 * 房东接口
 *
 * @author JoruaChan
 */
public interface Landlord {
    /**
     * 房东发布消息了
     *
     * @param message
     */
    void publish(String message);

    /**
     * 签订协议
     */
    void signAgreement();
}
