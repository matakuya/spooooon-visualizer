package com.example.mikake.visualizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinkermode.MODEData;

import java.io.IOException;

/**
 * ログイン情報を管理する必要があったのでSingletonクラスを用意する必要があった．
 *
 * ここを参考（てかコピペ）
 * https://github.com/moderepo/Android-SDK/blob/98c169b394533c705dfd96dbba02e6e759ea1b20/lumos/src/main/java/com/tinkermode/lumos/managers/DataHolder.java
 */

public class DataHolder {
    private static int projectId;
    private static boolean emailLogin;

    // You would need to setup appId according to your App settings.
    // The sample project pregenerates "controller_app" App. So you don't have to change the project
    // if you use it as it is.
    // Please see more detail (http://dev.tinkermode.com/tutorials/getting_started.html) to get them.
    public static final String appId = "controller_app";

    public static int getProjectId() {
        return projectId;
    }

    public static boolean isEmailLogin() {
        return emailLogin;
    }

    public static void setProjectId(int projectId) {
        DataHolder.projectId = projectId;
    }

    public static void setEmailLogin(boolean emailLogin) {
        DataHolder.emailLogin = emailLogin;
    }

    public static int getHomeId() {
        return homeId;
    }

    public static void setHomeId(int homeId) {
        DataHolder.homeId = homeId;
    }

    public static int homeId;

    static private String phoneNumber;
    static private MODEData.ClientAuthentication clientAuthentication;

    static private String email;
    static private String password;

    static private String APIHost = "api.tinkermode.com";

    static public void setAPIHost(String host) {
        APIHost = host;
    }

    static public String getAPIHost() {
        return APIHost;
    }

    static public void setPhoneNumber(String phoneNumber) {
        DataHolder.phoneNumber = phoneNumber;
    }

    static public void setEmail(String email) {
        DataHolder.email = email;
    }

    static public void setPassword(String password) {
        DataHolder.password = password;
    }

    static public String getEmail() {
        return DataHolder.email;
    }

    static public String getPassword() {
        return DataHolder.password;
    }

    static public String getPhoneNumber() {
        return DataHolder.phoneNumber;
    }

    static public void setClientAuthentication(MODEData.ClientAuthentication authentication) {
        DataHolder.clientAuthentication = authentication;
    }

    static public MODEData.ClientAuthentication getClientAuthentication() {
        return DataHolder.clientAuthentication;
    }

    final static String PREF_NAME = "MODE_DATA";
    final static String KEY_AUTH = "auth";
    final static String KEY_HOME_ID = "homeId";
    final static String KEY_PHONE_NUMBER = "phoneNumber";
    final static String KEY_PROJECT_ID = "projectId";
    final static String KEY_EMAIL_LOGIN = "emailLogin";
    final static String KEY_EMAIL = "email";
    final static String KEY_API_HOST = "APIHost";

    static public void saveData(Context context) {
        SharedPreferences modeData = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = modeData.edit();
        edit.clear();
        if (getClientAuthentication() != null) {
            edit.putString(KEY_AUTH, getClientAuthentication().toString());
        } else {
            edit.remove(KEY_AUTH);
        }

        if (getHomeId() != 0) {
            edit.putInt(KEY_HOME_ID, getHomeId());
        } else {
            edit.remove(KEY_HOME_ID);
        }

        if (getPhoneNumber() != null) {
            edit.putString(KEY_PHONE_NUMBER, getPhoneNumber());
        } else {
            edit.remove(KEY_PHONE_NUMBER);
        }

        if (getEmail() != null) {
            edit.putString(KEY_EMAIL, getEmail());
        } else {
            edit.remove(KEY_EMAIL);
        }

        if (getProjectId() != 0) {
            edit.putInt(KEY_PROJECT_ID, getProjectId());
        } else {
            edit.remove(KEY_PROJECT_ID);
        }

        if (getAPIHost() != "") {
            edit.putString(KEY_API_HOST, getAPIHost());
        } else {
            edit.remove(KEY_API_HOST);
        }

        edit.putBoolean(KEY_EMAIL_LOGIN, isEmailLogin());

        Log.e(DataHolder.class.getSimpleName(), "email login: " + isEmailLogin());

        edit.apply();
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    static public void loadData(Context context) {
        try {
            final SharedPreferences modeData = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            setProjectId(modeData.getInt(KEY_PROJECT_ID, 0));
            setEmailLogin(modeData.getBoolean(KEY_EMAIL_LOGIN, false));

            final String token = modeData.getString(KEY_AUTH, "");
            if (!token.equals("")) {
                setClientAuthentication(mapper.readValue(token, MODEData.ClientAuthentication.class));
            }
            setHomeId(modeData.getInt(KEY_HOME_ID, 0));
            setPhoneNumber(modeData.getString(KEY_PHONE_NUMBER, ""));
            setEmail(modeData.getString(KEY_EMAIL, ""));
            setAPIHost(modeData.getString(KEY_API_HOST, "api.tinkermode.com"));

        } catch (JsonMappingException e) {
            Log.e(DataHolder.class.getSimpleName(), "Mapping is wrong : " + e.toString());
        } catch (JsonParseException e) {
            Log.e(DataHolder.class.getSimpleName(), "Parsing is wrong : " + e.toString());
        } catch (IOException e) {
            Log.e(DataHolder.class.getSimpleName(), "IOException : " + e.toString());
        }
    }
}
