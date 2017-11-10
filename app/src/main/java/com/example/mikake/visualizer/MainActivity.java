package com.example.mikake.visualizer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

// Http
import com.example.mikake.visualizer.HttpResponseAsync;

public class MainActivity extends AppCompatActivity {
    private LineChart lineChart;
    private SeekBar mSeekBarX;
    private TextView tvX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init LineChart
        lineChart = (LineChart)findViewById(R.id.chart);

        // Init LineDataSet
        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(60f,0));
        entries.add(new Entry(50f,1));
        entries.add(new Entry(58f,2));
        entries.add(new Entry(60f,3));
        entries.add(new Entry(65f,4));
        entries.add(new Entry(80f,5));
        entries.add(new Entry(78f,6));
        LineDataSet lineDataSet = new LineDataSet(entries, "weight");

        // Init LineData
        String[] labels = {"2015","2016","2017","2018","2019","2020","2021"};
        LineData lineData = new LineData(labels, lineDataSet);

        // Set LineData to LineChart
        lineChart.setData(lineData);

        lineChart.setDescription("体重の遷移");
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.animateX(1200);

        // For API
//        HttpResponseAsync responseAsync = new HttpResponseAsync();
//        responseAsync.execute();
    }
}
