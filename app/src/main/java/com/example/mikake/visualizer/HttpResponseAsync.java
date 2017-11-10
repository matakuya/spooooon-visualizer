package com.example.mikake.visualizer;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



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

/**
 * Created by mikake on 17/11/10.
 */

public class HttpResponseAsync extends AsyncTask<Void, Void, JSONArray> {

    /**
     * 呼び出し元のActivity
     */
    private Activity activity;

    /**
     * Constructor
     */
    public HttpResponseAsync(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        HttpURLConnection con = null;
        URL url = null;
        JSONArray jsonArray = null;
        String urlSt = "http://222.158.198.94:5000/log/1";

        try {
            // URLの作成
            url = new URL(urlSt);
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection)url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("GET");
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            // URL接続からデータを読み取る場合はtrue
            con.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
            // false にしないとPOSTになるよ
            con.setDoOutput(false);

            // 接続
            con.connect(); // ①
            // 本文の取得
//            InputStream in = con.getInputStream();
//            byte bodyBite[] = new byte[1024];
//            in.read(bodyBite);
//            Log.d("response", new String(bodyBite, "UTF-8"));
//            in.close();

            // JSONを用いる場合
            InputStream in = con.getInputStream();
            String readst = readInputStream(in);
            jsonArray = new JSONObject(readst).getJSONArray("logs");
            Log.d("JSON", jsonArray.toString());
            return jsonArray;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        // ここに非同期処理を行って取得したデータを処理する

        for (int i = 0; i < result.length(); i++) {
            try {
                JSONObject jsonObject = result.getJSONObject(i);
                String value = jsonObject.getString("value");
                String timestamp = jsonObject.getString("timestamp");
                Log.d("value", value);
                Log.d("timestamp", timestamp);
            } catch (JSONException e){
                e.printStackTrace();
            }

        }
        // Init LineChart
        LineChart lineChart = (LineChart)activity.findViewById(R.id.chart);

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
    }

    public String readInputStream(InputStream in) throws IOException, UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        String st = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        while ((st = br.readLine()) != null)
        {
            sb.append(st);
        }
        try
        {
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return sb.toString();
    }

}
