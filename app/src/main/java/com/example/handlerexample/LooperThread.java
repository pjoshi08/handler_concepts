package com.example.handlerexample;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class LooperThread extends Thread {

    static final int TASK1 = 1;
    static final int TASK2 = 2;
    static final int TASK3 = 3;
    private static final String TAG = "LooperThread";
    private Handler bgThreadHandler;
    private Handler mainThreadHandler;

    LooperThread(Handler mainThreadHandler) {
        super(TAG);
        this.mainThreadHandler = mainThreadHandler;
        start();
    }

    @Override
    public void run() {
        Looper.prepare();

        bgThreadHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Message message = Message.obtain();
                switch (msg.what) {
                    case TASK1:
                        threadSleep(1000);

                        message.obj = "Task1 executed";
                        msg.what = TASK1;
                        mainThreadHandler.sendMessage(message);
                        break;
                    case TASK2:
                        threadSleep(2000);

                        message.obj = "Task2 executed";
                        msg.what = TASK2;
                        mainThreadHandler.sendMessage(message);
                        break;

                    case TASK3:
                        threadSleep(2000);

                        quit();
                        break;
                }
            }
        };

        Looper.loop();
    }

    private void threadSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void quit() {
        // Looper.quit() is called
        bgThreadHandler.getLooper().quit();
        Message message = Message.obtain();
        message.obj = "Looper Terminated";
        mainThreadHandler.sendMessage(message);
    }

    Handler getBgThreadHandler() {
        return bgThreadHandler;
    }
}
