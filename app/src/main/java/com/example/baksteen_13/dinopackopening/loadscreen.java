package com.example.baksteen_13.dinopackopening;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class loadscreen extends AppCompatActivity {

    private ProgressBar mLoadbar;

    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadscreen);

        mLoadbar = (ProgressBar) findViewById(R.id.Loadbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mProgressStatus < 100){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(40);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mLoadbar.setProgress(mProgressStatus);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(loadscreen.this, ChooseActivity.class));

                    }
                });
            }
        }).start();
    }
}
