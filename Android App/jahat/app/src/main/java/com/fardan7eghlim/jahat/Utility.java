package com.fardan7eghlim.jahat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Amir on 1/14/2017.
 */

public class Utility {
    public Utility(Context context) {
        this.context = context;
    }
    public Utility() {
    }

    private static final String TAG = Utility.class.getSimpleName();
    private ProgressDialog pDialog;
    private Context context;

    public void getVersion(final MainActivity.CallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "URL_getVersionOFAndroid";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_getVersionOFAndroid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // links successfully fetched
                        Double version = jObj.getDouble("version");
                        boolean newVersionisAvi=false;
                        if(version>AppConfig.version){
                            newVersionisAvi=true;
                        }
                        String link4update="";
                        boolean forceUpdate = false;
                        if(newVersionisAvi){
                            link4update = jObj.getString("link");
                            int pre_version=AppConfig.version.intValue();
                            int last_version=version.intValue();
                            if(pre_version<last_version)
                                forceUpdate = true;
                        }
                        //jilipili
                        boolean jilipili_active = jObj.getBoolean("jilipili");
                        final SharedPreferences mPrefs = context.getSharedPreferences("label", 0);
                        Double App_versionAd1 = new Double(mPrefs.getString("App_versionAd1", "0.1"));
                        boolean Ad1ok = jObj.getBoolean("Ad1ok");
                        Double versionAd1 = jObj.getDouble("versionAd1");
                        String linkAd1=null;
                        if(Ad1ok && versionAd1>App_versionAd1){
                            linkAd1=jObj.getString("linkAd1");
                            SharedPreferences.Editor mEditor = mPrefs.edit();
                            mEditor.putString("App_versionAd1", versionAd1.toString()).commit();
                        }
                        Double App_versionAd2 = new Double(mPrefs.getString("App_versionAd2", "0.1"));
                        boolean Ad2ok = jObj.getBoolean("Ad2ok");
                        Double versionAd2 = jObj.getDouble("versionAd2");
                        String linkAd2=null;
                        if(Ad2ok && versionAd2>App_versionAd2){
                            linkAd2=jObj.getString("linkAd2");
                            SharedPreferences.Editor mEditor = mPrefs.edit();
                            mEditor.putString("App_versionAd2", versionAd2.toString()).commit();
                        }
                        callBack.onSuccess(link4update,newVersionisAvi,forceUpdate,jilipili_active,Ad1ok,linkAd1,jObj.getString("linkOAd1"),Ad2ok,linkAd2,jObj.getString("linkOAd2"));
                    } else {

                        // Error occurred in fetching links. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("getVersion", "1");

                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    //check network
    public static boolean isNetworkAvailable(Context context){
        boolean available = false;
        /** Getting the system's connectivity service */
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        /** Getting active network interface  to get the network's status */
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isAvailable())
            available = true;
        /** Returning the status of the network */
        return available;
    }
    //convert types
    public static String convertType(int typeId){
        String s="";
        switch(typeId){
            case 1:
                s="کانال";
                break;
            case 2:
                s="گروه عمومی";
                break;
            case 3:
                s="ربات";
                break;
            default:
                s="نامشخص";
        }
        return s;
    }
    public void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
        context.startActivity(launchBrowser);
    }
    //get info of channel
    public void getInfoOFChannel(final AddNewActivity.CallBack callBack,final String link){
        // Tag used to cancel the request
        String tag_string_req = "URL_getInfoOFChannel";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_getInfoOFChannel, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String info=null;
                    if (!error) {
                        info = jObj.getString("info");
                        callBack.onSuccess(info);
                    } else {

                        // Error occurred in fetching links. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("getInfoOFchannel", "1");
                params.put("jZTw", "1");
                params.put("link", link);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    ///convert bitmap to string
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    //convert String to bitmap
    public Bitmap getStringImage(String temp){
        byte[] decodedString = Base64.decode(temp, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
