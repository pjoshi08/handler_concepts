package com.example.handlerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvMessage;
    private Worker worker;
    private Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.tvMessage);

        setupHandler();

        worker = new Worker();

        worker.execute(() -> {
            Message message = Message.obtain();
            threadSleep(1000);

            message.obj = "Task1 executed";
            mainThreadHandler.sendMessage(message);
        }).execute(() -> {
            Message message = Message.obtain();
            threadSleep(2000);

            message.obj = "Task2 executed";
            mainThreadHandler.sendMessage(message);
        }).execute(() -> {
            Message message = Message.obtain();
            threadSleep(2000);

            if (worker.quit()){
                // Looper.quit() is called
                message.obj = "Looper Terminated";
                mainThreadHandler.sendMessage(message);
            }
        });
    }

    private void setupHandler() {
        mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tvMessage.setText((String)msg.obj);
            }
        };
    }

    private void threadSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
