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
    private LooperThread worker;
    private Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.tvMessage);

        setupHandler();

        instantiateWorker();

        findViewById(R.id.task1).setOnClickListener(v -> {
            Message message = Message.obtain();
            message.what = LooperThread.TASK1;
            worker.getBgThreadHandler().sendMessage(message);
        });

        findViewById(R.id.task2).setOnClickListener(v -> {
            Message message = Message.obtain();
            message.what = LooperThread.TASK2;
            worker.getBgThreadHandler().sendMessage(message);
        });

        findViewById(R.id.task3).setOnClickListener(v -> {
            Message message = Message.obtain();
            message.what = LooperThread.TASK3;
            worker.getBgThreadHandler().sendMessage(message);
        });
    }

    private void instantiateWorker() {
        worker = new LooperThread(mainThreadHandler);
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
}
