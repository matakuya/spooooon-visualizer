package com.example.mikake.visualizer;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

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

/**
 * Created by mikake on 17/11/10.
 */

public class HttpResponseAsync extends AsyncTask<Void, Void, String> {

    private TextView textView;

    /**
     * Constructor
     */
    public HttpResponseAsync() {
        super();
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection con = null;
        URL url = null;
        JSONObject jsonObject = null;
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
            jsonObject = new JSONObject(readst).getJSONObject("log");
            Log.d("JSON", jsonObject.toString());
            return jsonObject.toString();
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
    protected void onPostExecute(String result) {
        // ここに非同期処理を行って取得したデータを処理する
        textView.setText(String.valueOf(result));
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
