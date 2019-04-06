package com.example.handlerexample;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleWorker extends Thread {

    private static final String TAG = "SimpleWorker";
    private AtomicBoolean alive = new AtomicBoolean(true);
    private ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    SimpleWorker() {
        super(TAG);
        start();
    }

    @Override
    public void run() {
        while (alive.get()) {
            Runnable task = tasks.poll();
            if (task != null)
                task.run();
        }
        Log.i(TAG, "Program terminated");
    }

    SimpleWorker execute(Runnable task) {
        tasks.add(task);
        return this;
    }

    void quit() {
        alive.set(false);
    }
}
