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

import java.util.List;

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
        MODEApp.setAPIHost(DataHolder.getAPIHost());
        //WebSocket用のAPIエンドポイント指定
        MODEEventListener.setWebsocketHost(DataHolder.getAPIHost());

        final TextView emailText = (TextView) findViewById(R.id.email);
        final TextView passwordText = (TextView) findViewById(R.id.password);
        MODEApp.authenticateWithEmail(
                getApplicationContext(),
                DataHolder.getProjectId(),
                emailText.getText().toString(),
                passwordText.getText().toString(),
                DataHolder.appId,
                new MODEApp.Completion<MODEData.ClientAuthentication>() {
                    @Override
                    public void done(MODEData.ClientAuthentication ret, Throwable e) {
                        if (e != null) {
                            Log.d("Login", "Error");
//                            MiscUtils.showAlert(getApplicationContext(), getClass(), e);
                            passwordText.setError(getResources().getString(R.string.error_incorrect_password));
                        } else {
                            Log.d("Login", "Success");
                            Log.d("Login", ret.toString());

//                            logger.d("Succeeded authentication: " + ret.toString());
                            DataHolder.setClientAuthentication(ret);
//                            DeviceManager.getInstance().startListenToEvents(ret);
                            DataHolder.saveData(getApplicationContext());
//

                            fetchHomes();
                            fetchDevices();
//                            LoginActivity loginActivity = weakReference.get();
//                            if (loginActivity != null) {
//                                final Intent intent = new Intent(loginActivity, MainActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                            }
//                            progressDialog.hide();
//
//                            logger.d("Succeeded to initiate auth: " + ret.toString());

                            // Return to MainActivity
                            finish();
                        }
                    }
                });
    }

    private void fetchDevices() {
        MODEApp.getDevices(
                getApplicationContext(),
                DataHolder.getClientAuthentication(),
                DataHolder.getHomeId(),
                new MODEApp.Completion<List<MODEData.Device>>() {
                    @Override
                    public void done(List<MODEData.Device> ret, Throwable e) {
                        if (e != null) {
                            Log.d("Fetch Devices", "Error");
                        } else {
                            Log.d("Fetch devices", "Success");
                            for (MODEData.Device device : ret) {
                                Log.d("Device", device.toString());
                            }
//                            DeviceManager.getInstance().queryDeviceStatus(getActivity(), ret);
//                            postUpdate((List<MODEData.MODEObject>)(List<?>)ret);
                        }
                    }
                });
    }

    private void fetchHomes() {
        MODEApp.getHomes(
                getApplicationContext(),
                DataHolder.getClientAuthentication(),
                DataHolder.getClientAuthentication().userId,
                new MODEApp.Completion<List<MODEData.Home>>() {
                    @Override
                    public void done(List<MODEData.Home> ret, Throwable e) {
                        if (e != null) {
                            Log.d("fetch Homes","Error");
                        } else {
                            Log.d("Fetch Homes", "Success");
                            for (MODEData.Home home : ret) {
                                Log.d("Home", home.toString());
                                DataHolder.setHomeId(home.homeId);
                            }
                            Log.d("Home Id", Integer.toString(DataHolder.getHomeId()));
                        }
                    }
                });
    }
}
