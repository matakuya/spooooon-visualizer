package com.example.mikake.visualizer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
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
import java.util.List;

/**
 * Created by mikake on 17/11/10.
 */

/*
 * AsyncTask<型1, 型2,型3>
 *
 *   型1 … Activityからスレッド処理へ渡したい変数の型
 *          ※ Activityから呼び出すexecute()の引数の型
 *          ※ doInBackground()の引数の型
 *
 *   型2 … 進捗度合を表示する時に利用したい型
 *          ※ onProgressUpdate()の引数の型
 *
 *   型3 … バックグラウンド処理完了時に受け取る型
 *          ※ doInBackground()の戻り値の型
 *          ※ onPostExecute()の引数の型
 *
 *   ※ それぞれ不要な場合は、Voidを設定すれば良い
 */
public class EvaluateResponseAsync extends AsyncTask<String, Void, JSONObject> {

    /**
     * 呼び出し元のActivity
     */
    private Activity activity;

    /**
     * Constructor
     */
    public EvaluateResponseAsync(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        HttpURLConnection con = null;
        URL url = null;
        JSONObject jsonObject = null;
        String id = params[0];
        String urlSt = "http://222.158.198.94:5000/evaluate/" + id;
        Log.d("URL", urlSt);
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
            jsonObject = new JSONObject(readst).getJSONObject("evaluation");
            Log.d("JSON", jsonObject.toString());
            return jsonObject;
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
    protected void onPostExecute(JSONObject result) {
        // ここに非同期処理を行って取得したデータを処理する
        if (result != null) {
            String evaluation = "";
            Log.d("DEBUG", "evaluate start");
            try {
                evaluation = result.getString("evaluation");
                Log.d("DEBUG", evaluation);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ImageView imageView = (ImageView)activity.findViewById(R.id.icon);
            //Do some stuff with it, then when memory gets low:
//            ((BitmapDrawable)imageView.getDrawable()).getBitmap().recycle();
            TextView textView = (TextView) activity.findViewById(R.id.line);
//            Drawable drawable = null;
            Bitmap bitmap = null;
            Log.d("DEBUG", evaluation);
            switch (evaluation) {
                case "hot":
                    Log.d("EVALUATE", evaluation);
                    bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.hot);
                    textView.setText("熱すぎるものを食べていませんか");
                    break;
                case "nomal":
                    Log.d("EVALUATE", evaluation);
                    bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.hot);
//                    drawable = activity.getResources().getDrawable(R.drawable.normal);
                    textView.setText("丁度いいですね");
                    break;
                case "cold":
                    Log.d("EVALUATE", evaluation);
                    bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.hot);
//                    drawable = activity.getResources().getDrawable(R.drawable.cold);
                    textView.setText("冷たすぎるものを食べていませんか");
                    break;
            }
            int height = (bitmap.getHeight() * 256 / bitmap.getWidth());
            Bitmap scale = Bitmap.createScaledBitmap(bitmap, 256, height, true);
            imageView.setImageBitmap(scale);
            //Do some stuff with it, then when memory gets low:
//            ((BitmapDrawable)imageView.getDrawable()).getBitmap().recycle();
//              imageView.setImageDrawable(drawable);
        }
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
