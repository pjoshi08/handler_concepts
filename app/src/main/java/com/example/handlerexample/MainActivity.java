package com.example.handlerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvMessage;
    private SimpleWorker worker;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.tvMessage);

        setupHandler();

        initiateTasks();
    }

    private void initiateTasks() {
        worker = new SimpleWorker();

        worker.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message = Message.obtain();
            message.obj = "Task 1 Runnable";
            handler.sendMessage(message);
        }).execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message = Message.obtain();
            message.obj = "Task 2 Runnable";
            handler.sendMessage(message);
        }).execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message = Message.obtain();
            message.obj = "Task 3 Runnable";
            handler.sendMessage(message);
        });
    }

    private void setupHandler() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tvMessage.setText((String)msg.obj);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        worker.quit();
    }
}
