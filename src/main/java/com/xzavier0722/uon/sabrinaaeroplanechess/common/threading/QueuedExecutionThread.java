package com.xzavier0722.uon.sabrinaaeroplanechess.common.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class QueuedExecutionThread {

    private final Thread t;
    private final BlockingQueue<QueuedTask> queue;

    public QueuedExecutionThread() {
        this.queue = new LinkedBlockingDeque<>();
        this.t = new Thread(() -> {
            while (true) {
                try {
                    QueuedTask task = queue.take();
                    task.execute();
                    Thread.sleep(task.getDelay());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void start() {
        t.start();
    }

    public boolean schedule(QueuedTask task) {
        return queue.offer(task);
    }

    public int getQueuedCount() {
        return queue.size();
    }

    public void abort() {
        t.interrupt();
    }

}
