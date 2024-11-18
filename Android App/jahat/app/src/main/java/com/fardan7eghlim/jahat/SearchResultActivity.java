package com.fardan7eghlim.jahat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultActivity extends Activity {
    private static final String TAG = Utility.class.getSimpleName();
    private ProgressDialog pDialog;
    private int lim=0;
    private ListView lv;
    private List<ChatObj> listOfChatObj=new ArrayList<ChatObj>();
    private String filter;
    private String type;
    private boolean flag_firstCome=true;
    private boolean firstCome=true;
    private View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        lv=(ListView) findViewById(R.id.LinkslistView);
		footerView = ((LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        //flag_firstCome=true;

        Bundle extras = getIntent().getExtras();
        filter=extras.getString("filter");
        type=extras.getString("type");

        search();
    }
    //searching
    private void search() {
        // Tag used to cancel the request
        final String tag_string_req = "select_one";
        final Context context=getApplicationContext();
		lv.removeFooterView(footerView);
        pDialog.setMessage("در حال جست و جو...");
        showDialog();
        if(firstCome) {
            firstCome = false;
        }else{
            lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            lv.setStackFromBottom(true);
        }

        final StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_search, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        //fetched successfully
						addFooterView();
						int sizeList=listOfChatObj.size();
                        JSONArray table = jObj.getJSONArray("table");
                        for (int i = 0; i < table.length(); i++) {
                            JSONObject jsonobject = table.getJSONObject(i);
                            String id=jsonobject.getString("id");
                            String unique_id=jsonobject.getString("unique_id");
                            String name=jsonobject.getString("name");
                            String description=jsonobject.getString("description");
                            String link=jsonobject.getString("link");
                            String type=jsonobject.getString("type");
                            String score=jsonobject.getString("score");
                            Bitmap ava=getStringImage(jsonobject.getString("avatar"));
                            String numberMember=jsonobject.getString("numberMember");
                            String created_at=jsonobject.getString("created_at");
                            String updated_at=jsonobject.getString("updated_at");
                            listOfChatObj.add(new ChatObj(id,unique_id,name,link,numberMember,score,description,type,created_at,updated_at,ava,true));
                        }
						if(listOfChatObj.size()-sizeList<10){
							lv.removeFooterView(footerView);
						}
                    } else {
                        // Error occurred in adding link. Get the error
                        // message
                        if(jObj.has("error_msg"))
                        Toast.makeText(context,
                                jObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                                if(flag_firstCome) finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideDialog();
                onContinue();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params
                Map<String, String> params = new HashMap<String, String>();
                params.put("filter", filter);
                params.put("lim", lim+"");
                params.put("type", type);

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
	private void addFooterView() {
        lv.addFooterView(footerView);
        LinearLayout footer_layout = (LinearLayout) findViewById(R.id.footer_layout);
        footer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Utility().isNetworkAvailable(getApplicationContext())) {
                    lim += 10;
                    search();
                } else {
                    Toast.makeText(getApplicationContext(),"اینترنت در دسترس نمی باشد", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void onContinue(){
        flag_firstCome=false;
        if(listOfChatObj.size()!=0){
            //save current filter
            final SharedPreferences mPrefs = getSharedPreferences("label", 0);
            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putString("filter_spt", filter).commit();
            mEditor.putString("type_spt", type).commit();
            //make list
            lv.setAdapter(new CustomAdapterLinks(this, listOfChatObj));
            lv.invalidateViews();
            if(listOfChatObj.size()<3){
                lv.setStackFromBottom(false);
            }
        }
    }
    //convert String to bitmap
    public Bitmap getStringImage(String temp){
        byte[] decodedString = Base64.decode(temp, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
