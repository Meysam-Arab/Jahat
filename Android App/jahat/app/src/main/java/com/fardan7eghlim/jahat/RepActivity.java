package com.fardan7eghlim.jahat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RepActivity extends AppCompatActivity {

    private static final String TAG = Utility.class.getSimpleName();
    private ProgressDialog pDialog;
    private String channel_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        final String tag_string_req = "report";
        showDialog();

        Intent intent = getIntent();
        channel_Id  = intent.getStringExtra("channel_id");


        final StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_report, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jObj = new JSONObject(response);
                    String status = jObj.getString("status");

                    if (!status.equals("Error")) {
                        //fetched successfully
                        Toast.makeText(getApplicationContext(),
                                "گزارش با موفقیت ارسال شد", Toast.LENGTH_LONG).show();

                    } else {
                        // Error occurred in adding link. Get the error
                            Toast.makeText(getApplicationContext(),
                                   "عملیات با شکست مواجه شد", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideDialog();
                finish();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "پیام خودکار برنامه");
                params.put("email", "support@jahat.net");
                params.put("tel", "07132221402");
                params.put("mobile", "09171111111");
                params.put("title", "گزارش محتوای نامناسب");
                params.put("description", "آی دی کانال با محتوای نامناسب گزارش شده:" + channel_Id);

                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
