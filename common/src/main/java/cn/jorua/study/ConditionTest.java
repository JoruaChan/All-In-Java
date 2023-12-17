package cn.jorua.study;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class ConditionTest {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void runAwait() {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Run Await...." + Thread.currentThread().getName());
                condition.await();
                System.out.println("Continue Run...." + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionTest conditionTest = new ConditionTest();
        for (int i = 0; i < 5; i++) {
            conditionTest.runAwait();
        }

        Thread.sleep(1000);
        conditionTest.lock.lock();
        try {
            System.out.println("Start to singal all");
            conditionTest.condition.signalAll();
            System.out.println("End to singal all");
        } finally {
            conditionTest.lock.unlock();
        }

    }
}
