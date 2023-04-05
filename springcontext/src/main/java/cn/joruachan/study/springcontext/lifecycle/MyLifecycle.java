package cn.joruachan.study.springcontext.lifecycle;

import org.springframework.context.Lifecycle;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class MyLifecycle implements Lifecycle {

    private AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void start() {
        running.set(true);
        System.out.println("MyLifecycle#start");
    }

    @Override
    public void stop() {
        running.set(false);
        System.out.println("MyLifecycle#stop");
    }

    @Override
    public boolean isRunning() {
        System.out.println("MyLifecycle#isRunning");
        return running.get();
    }
}
