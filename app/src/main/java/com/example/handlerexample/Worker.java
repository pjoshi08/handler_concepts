package com.example.handlerexample;

import android.os.Handler;
import android.os.HandlerThread;

class Worker extends HandlerThread {
    private static final String TAG = "Worker";
    private Handler bgThreadHandler;

    Worker() {
        super(TAG);
        start();
        bgThreadHandler = new Handler(getLooper());
    }

    Worker execute(Runnable task) {
        bgThreadHandler.post(task);
        return this;
    }
}
