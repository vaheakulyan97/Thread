package com.example.hp.dowload;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private EditText editText;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.id_wait_text);
        editText = findViewById(R.id.id_edit_text);
        final Button exitButton = findViewById(R.id.exitButton);
        final Button button = findViewById(R.id.id_enter_button);

        progressBar = findViewById(R.id.id_progres_bar);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyThread myThread = new MyThread();
                myThread.start();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onPostResume() {
        super.onPostResume();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    if (msg.arg1==0){
                        textView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        textView.setText("Please wait for "+ editText.getText().toString() + " seconds");
                    }
                     progressBar.setProgress(msg.arg1);
                    if (msg.arg1== Integer.parseInt(editText.getText().toString()) - 1){
                        progressBar.setVisibility(View.INVISIBLE);
                        textView.setText("Finished " );
                    }
                }
            }

        };
    }

    private class MyThread extends Thread {
        public void run() {
            for (int i = 0; i < Integer.parseInt(editText.getText().toString()); i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 0;
                message.arg1 = i;
                handler.sendMessage(message);
            }
        }
    }

}



