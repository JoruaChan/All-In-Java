package cn.joruachan.study.sync;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class SynchronizedThread extends Thread {
    private boolean stop = false;

    @Override
    public void run() {
        while (!stop) {
            cycle();
        }
    }

    public synchronized void cycle() {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void handStop() {
        this.stop = true;
    }
}
