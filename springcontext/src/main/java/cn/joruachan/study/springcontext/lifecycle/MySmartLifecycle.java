package cn.joruachan.study.springcontext.lifecycle;

import org.springframework.context.SmartLifecycle;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class MySmartLifecycle implements SmartLifecycle {

    private AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void start() {
        running.set(true);
        System.out.println("MySmartLifecycle#start");
    }

    @Override
    public void stop() {
        running.set(false);
        System.out.println("MySmartLifecycle#stop");
    }

    @Override
    public boolean isRunning() {
        System.out.println("MySmartLifecycle#isRunning");
        return running.get();
    }
}
