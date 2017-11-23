package com.example.mikake.visualizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import com.tinkermode.MODEApp;
import com.tinkermode.MODEData;
import com.tinkermode.MODEEventListener;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView emailText = (TextView) findViewById(R.id.email);
        TextView passwordText = (TextView) findViewById(R.id.password);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }

    private void login(View view) {
        //IoTデバイスハブのAPIエンドポイントを指定する
        MODEApp.setAPIHost("iot-device.jp-east-1.api.cloud.nifty.com");

        //WebSocket用のAPIエンドポイント指定
        MODEEventListener.setWebsocketHost("iot-device.jp-east-1.api.cloud.nifty.com");

        TextView emailText = (TextView) findViewById(R.id.email);
        TextView passwordText = (TextView) findViewById(R.id.password);
        MODEApp.authenticateWithEmail(
                getApplicationContext(),
                189,
                emailText.getText().toString(),
                passwordText.getText().toString(),
                "espwroom32apuri",
                new MODEApp.Completion<MODEData.ClientAuthentication>() {
                    @Override
                    public void done(MODEData.ClientAuthentication ret, Throwable e) {
                        if (e != null) {
                            Log.d("Login", "Error");
//                            MiscUtils.showAlert(getApplicationContext(), getClass(), e);
                        } else {
                            Log.d("Login", "Success");
                            Log.d("Login", ret.toString());



                            // Return MainActivity
                            finish();


//                            logger.d("Succeeded authentication: " + ret.toString());
//                            DataHolder.setClientAuthentication(ret);
//                            DeviceManager.getInstance().startListenToEvents(ret);
//                            DataHolder.saveData(getApplicationContext());
//
//                            LoginActivity loginActivity = weakReference.get();
//                            if (loginActivity != null) {
//                                final Intent intent = new Intent(loginActivity, MainActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                            }
//                            progressDialog.hide();
//
//                            logger.d("Succeeded to initiate auth: " + ret.toString());
                        }
                    }
                });
    }

//    private void fetchDevices() {
//        MODEApp.getDevices(
//
//        )
//    }
//
//    private void fetchHomes() {
//        MODEApp.getHomes()
//    }
}
