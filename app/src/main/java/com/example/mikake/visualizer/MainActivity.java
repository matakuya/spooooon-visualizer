package com.example.mikake.visualizer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


// Http
import com.example.mikake.visualizer.HttpResponseAsync;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SeekBar mSeekBarX;
    private TextView tvX;
    private String id = "4";

    private Timer timer = null;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Login
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

        setContentView(R.layout.activity_main);

        // For API
        HttpResponseAsync responseAsync = new HttpResponseAsync(MainActivity.this);
        responseAsync.execute("hour", "hot", this.id);

        timer = new Timer(true);
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                handler.post( new Runnable() {
                    public void run() {
                        //ここに処理を書く
                        // For Evaluate
//                        EvaluateResponseAsync evaluateResponseAsync = new EvaluateResponseAsync(MainActivity.this);
//                        evaluateResponseAsync.execute("4");
                    }
                });
            }
        },1000,3000); //1秒後から5秒間隔で実行

        findViewById(R.id.month).setOnClickListener(this);
        findViewById(R.id.day).setOnClickListener(this);
        findViewById(R.id.hour).setOnClickListener(this);

        // 画面遷移
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            HttpResponseAsync responseAsync = new HttpResponseAsync(MainActivity.this);
            switch (view.getId()) {
                case R.id.month:
                    // For API
                    responseAsync.execute("month", "hot", this.id);
                    break;
                case R.id.day:
                    // For API
                    responseAsync.execute("day", "hot", this.id);
                    break;
                case R.id.hour:
                    // For API
                    responseAsync.execute("hour", "hot", this.id);
                    break;
                default:
                    break;
            }
        }
    }
}
