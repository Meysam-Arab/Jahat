package com.fardan7eghlim.jahat;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private ImageView search_btn;
    private Spinner types;
    private Button add_btn;
    private Button edit_btn;
    private Button lastSearch_btn;
    private Button aboutUs_btn;
    private Button bestChannels_btn;
    private Button changeLang_btn;
    private Button exit_btn;
    private EditText search_et;
    private ImageView logo_jilipili;
    private TextView link_jilipili;
    private boolean haveEverSeenJilipili;
    private int countSearch;
    private AsyncTask<String, Integer, Boolean> asyncTask;
    private static final String TAG = Utility.class.getSimpleName();
    private ProgressDialog pDialog;
    private Dialog d;
    private ImageView mad01;
    private ImageView mad02;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneSignal.startInit(this).init();
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .build();
        ImageLoader.getInstance().init(config);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //intilize view
        search_btn= (ImageView) findViewById(R.id.search_btn);
        search_btn.setImageResource(R.drawable.search);
        add_btn= (Button) findViewById(R.id.btnAdd);
        edit_btn= (Button) findViewById(R.id.btnEdit);
        lastSearch_btn= (Button) findViewById(R.id.btnLastSearch);
        aboutUs_btn= (Button) findViewById(R.id.btnAboutUs);
        bestChannels_btn= (Button) findViewById(R.id.btnBestChannels);
        changeLang_btn= (Button) findViewById(R.id.btnChangeLang);
        exit_btn= (Button) findViewById(R.id.btnExit);
        search_et= (EditText) findViewById(R.id.search_et);
        logo_jilipili= (ImageView) findViewById(R.id.jilipili_logo);
        link_jilipili=(TextView) findViewById(R.id.jilipili_title);
        AppConfig.avatar=null;
        //fill spinner
        addItemsOnSpinner();
        //btnAddNew
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddNewActivity.class);
                startActivity(intent);
            }
        });

        //prevent user to follow jilipili
        final SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String haveSnJili_st = mPrefs.getString("haveSnJili_st", "0");
        if(haveSnJili_st.equals("0"))
            haveEverSeenJilipili=false;
        else
            haveEverSeenJilipili=true;
        //jili_pili
        logo_jilipili.setImageResource(R.drawable.jilipili_logo);
        link_jilipili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("haveSnJili_st", "1").commit();
                new Utility(getApplicationContext()).goToUrl ( "https://telegram.me/jili_pili");
            }
        });
        //first time view -helper
        String haveSnHelper01 = mPrefs.getString("haveSnHelper01", "0");
        if(haveSnHelper01.equals("0")){
            d = new Dialog(MainActivity.this);
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.help_start_01);
            ImageView helper01= (ImageView) d.findViewById(R.id.imageViewHelper01);
            helper01.setImageResource(R.drawable.help01);
            ImageView helper02= (ImageView) d.findViewById(R.id.imageViewHelper02);
            helper02.setImageResource(R.drawable.help02);
            d.show();
            Button btn_helper01= (Button) d.findViewById(R.id.btnHelper01);
            btn_helper01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("haveSnHelper01", "1").commit();
                }
            });
        }
        //btnEdit
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new Dialog(MainActivity.this);
                findBeforeEdit();
            }
        });
        //btnLastSearch
        lastSearch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get last Filter
                String filter_st = mPrefs.getString("filter_spt", "0h67sda3dadasd0");
                String type_st = mPrefs.getString("type_spt", "0");
                if(!filter_st.equals("0h67sda3dadasd0")){
                    pushSearch(filter_st,type_st);
                }
            }
        });
        //btnAboutUs
        aboutUs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AboutUsActivity.class);
                startActivity(intent);
            }
        });
        //listOfAdmins_btn
        bestChannels_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "این امکان در آینده اضافه خواهد شد", Toast.LENGTH_SHORT).show();
            }
        });
        //changeLang_btn
        changeLang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "این امکان در آینده اضافه خواهد شد", Toast.LENGTH_SHORT).show();
            }
        });
        //btnExit
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //btnSearch
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!search_et.getText().toString().isEmpty())
                    pushSearch(search_et.getText().toString(),Long.toString(types.getSelectedItemId()));
            }
        });

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    if(!search_et.getText().toString().isEmpty())
                        pushSearch(search_et.getText().toString(),Long.toString(types.getSelectedItemId()));
                }
                return false;
            }
        });

        //asyn 01
        if(new Utility().isNetworkAvailable(getApplicationContext()))
            check4update();
    }
    //search action
    private void pushSearch(final String filter,final String type){
        if(!(new Utility().isNetworkAvailable(getApplicationContext()))) {
            Toast.makeText(getBaseContext(), "اینترنت در دسترس نیست!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        final SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String haveSnJili_st = mPrefs.getString("haveSnJili_st", "0");
        if(haveSnJili_st.equals("0"))
            haveEverSeenJilipili=false;
        else
            haveEverSeenJilipili=true;
        //control to see jilipili
        if(!haveEverSeenJilipili){
            String temp = mPrefs.getString("countSeaarch_st", "0");
            countSearch=Integer.parseInt(temp)+1;
            if(countSearch>4){
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("haveSnJili_st", "1").commit();
//                new Utility(getApplicationContext()).goToUrl ( "https://telegram.me/jili_pili");
            }else{
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("countSeaarch_st", countSearch+"").commit();
            }
        }
        //go to result page
        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("filter",filter);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    //select before edit
    private void selectOne(final String link, final String uud) {
        if(!(new Utility().isNetworkAvailable(getApplicationContext()))) {
            Toast.makeText(getBaseContext(), "اینترنت در دسترس نیست!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Tag used to cancel the request
        final String tag_string_req = "select_one";
        final Context context=getApplicationContext();

        pDialog.setMessage("در حال جست و جو...");
        showDialog();

        final StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_select, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // link successfully stored in MySQL
                        // Launch Main activity
                        Intent intent = new Intent(context, EditActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        JSONArray table = jObj.getJSONArray("table");
                        ChatObj chatObj = null;
                        for (int i = 0; i < table.length(); i++) {
                            JSONObject jsonobject = table.getJSONObject(i);
                            String id=jsonobject.getString("id");
                            String unique_id=jsonobject.getString("unique_id");
                            String name=jsonobject.getString("name");
                            String description=jsonobject.getString("description");
                            String link=jsonobject.getString("link");
                            String type=jsonobject.getString("type");
                            String score=jsonobject.getString("score");
                            AppConfig.avatar=getStringImage(jsonobject.getString("avatar"));
                            String created_at=jsonobject.getString("created_at");
                            String updated_at=jsonobject.getString("updated_at");
                            boolean isAuth=jsonobject.getString("isAuthenticated").equals("1");
                            chatObj=new ChatObj(id,unique_id,name,link,"0",score,description,type,created_at,updated_at,null,isAuth);
                        }
                        intent.putExtra("chatObj", chatObj);
                        d.hide();
                        startActivity(intent);
                    } else {
                        // Error occurred in adding link. Get the error
                        // message
                        Toast.makeText(context,
                                jObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideDialog();
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
                params.put("select", "1");
                params.put("jwTw", "1");
                params.put("unique_id", uud);
                params.put("link", link);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        types = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("همه");
        list.add("کانال (channel)");
        list.add("گروه عمومی (public group)");
        list.add("ربات (bot)");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_types,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(dataAdapter);
    }
    //find before edit
    private void findBeforeEdit() {
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.find_for_edit_window);
        final EditText titleEt= (EditText) d.findViewById(R.id.title_et);
        final EditText uniqEt= (EditText) d.findViewById(R.id.unqId_et);
        final Button btnFind4Edit= (Button) d.findViewById(R.id.btnEdit_find);

        btnFind4Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=titleEt.getText().toString();
                if(link.startsWith("@"))
                    link=link.substring(1);
                if(!link.isEmpty() && !uniqEt.getText().toString().isEmpty()){
                    selectOne(link,uniqEt.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(),"لطفا تمام قسمت ها را پر کنید!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        d.show();
    }
    ///check for version
    private void check4update(){
        //new version checker
        asyncTask=new AsyncTask<String, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                new Utility(getApplicationContext()).getVersion(new CallBack() {
                    @Override
                    public void onFail() {
                        // Do Stuff
                    }
                    @Override
                    public void onSuccess(final String link4Update, boolean newVersionisAvi, boolean forceUpdate, boolean jilipili_active, boolean Ad1ok, String linkAd1, final String linkOAd1, boolean Ad2ok, String linkAd2, final String linkOAd2) {
                        FrameLayout newVerAv= (FrameLayout) findViewById(R.id.newVersionMessage);
                        if(newVersionisAvi){
                            newVerAv.setVisibility(View.VISIBLE);
                            if(!link4Update.isEmpty()){
                                final TextView myClickableUrl = (TextView) findViewById(R.id.newVersionLink);
                                Button update= (Button) findViewById(R.id.btnUpdate);
                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Uri uri = Uri.parse(link4Update);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);
                                    }
                                });

                                if(forceUpdate){
                                    //force user to update
                                    Intent intent = new Intent(getApplicationContext(),mustUpdate.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("web", link4Update);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }else {
                            newVerAv.setVisibility(View.GONE);
                        }
                        //jilipili
                        LinearLayout jilipili_part= (LinearLayout) findViewById(R.id.jilipili_part);
                        if(jilipili_active){
                            jilipili_part.setVisibility(View.VISIBLE);
                        }else{
                            jilipili_part.setVisibility(View.GONE);
                        }
                        final SharedPreferences mPrefs = getSharedPreferences("label", 0);
                        //ad 1
                        if(Ad1ok){
                            if(linkAd1!=null){
                                ImageLoader imageLoader = ImageLoader.getInstance();
                                imageLoader.loadImage(linkAd1, new SimpleImageLoadingListener() {
                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        // Do whatever you want with Bitmap
                                        String s=FileProcessor.saveToInternalStorage(getApplicationContext(),loadedImage,"jahat_ad","jahat_ad1.png");
                                        showAd1(s,linkOAd1);
                                        SharedPreferences.Editor mEditor = mPrefs.edit();
                                        mEditor.putString("App_pathAd1", s).commit();
                                        mEditor.putString("App_linkAd1", linkOAd1).commit();
                                    }
                                });
                            }
                            showAd1(mPrefs.getString("App_pathAd1", ""),mPrefs.getString("App_linkAd1", ""));
                        }else{
                            mad01= (ImageView) findViewById(R.id.mad_01);
                            mad01.setVisibility(View.GONE);
                        }
                        //ad 2
                        if(Ad2ok){
                            if(linkAd2!=null){
                                ImageLoader imageLoader = ImageLoader.getInstance();
                                imageLoader.loadImage(linkAd2, new SimpleImageLoadingListener() {
                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        // Do whatever you want with Bitmap
                                        String s=FileProcessor.saveToInternalStorage(getApplicationContext(),loadedImage,"jahat_ad","jahat_ad2.png");
                                        showAd2(s,linkOAd2);
                                        SharedPreferences.Editor mEditor = mPrefs.edit();
                                        mEditor.putString("App_pathAd2", s).commit();
                                        mEditor.putString("App_linkAd2", linkOAd2).commit();
                                    }
                                });
                            }
                            showAd2(mPrefs.getString("App_pathAd2", ""),mPrefs.getString("App_linkAd2", ""));
                        }else{
                            mad02= (ImageView) findViewById(R.id.mad_02);
                            mad02.setVisibility(View.GONE);
                        }
                        //ad at all
                        FrameLayout mad_max= (FrameLayout) findViewById(R.id.mad_max);
                        if(!Ad1ok && !Ad2ok && !jilipili_active){
                            mad_max.setVisibility(View.GONE);
                        }
                        else{
                            mad_max.setVisibility(View.VISIBLE);
                        }
                    }
                });
                return true;
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
            }
        };
        asyncTask.execute();
    }
    public interface CallBack {
        void onSuccess(String s,boolean e,boolean e2,boolean e3,boolean e4,String s2,String s3,boolean e5,String s4,String s5);
        void onFail();
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    //convert String to bitmap
    public Bitmap getStringImage(String temp){
        byte[] decodedString = Base64.decode(temp, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    private void showAd1(String path, final String link){
        if(!path.isEmpty() && !path.equals("")) {
            mad01 = (ImageView) findViewById(R.id.mad_01);
            mad01.setVisibility(View.VISIBLE);
            Bitmap ad1 = FileProcessor.loadImageFromStorage(path, "jahat_ad1.png");
            mad01.setImageBitmap(ad1);
            mad01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Utility(getApplicationContext()).goToUrl(link);
                }
            });
            LinearLayout mad_part = (LinearLayout) findViewById(R.id.mad_part);
            mad_part.setVisibility(View.VISIBLE);
        }
    }
    private void showAd2(String path, final String link){
        if(!path.isEmpty() && !path.equals("")) {
            mad02 = (ImageView) findViewById(R.id.mad_02);
            mad02.setVisibility(View.VISIBLE);
            Bitmap ad2 = FileProcessor.loadImageFromStorage(path, "jahat_ad2.png");
            mad02.setImageBitmap(ad2);
            mad02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Utility(getApplicationContext()).goToUrl(link);
                }
            });
            LinearLayout mad_part = (LinearLayout) findViewById(R.id.mad_part);
            mad_part.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast toast = Toast.makeText(getApplicationContext(),"جهت خروج دوباره دکمه بازگشت را انتخاب نمایید",Toast.LENGTH_LONG);
        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(13);
        toast.show();
        //////////////////////////////////////////////////////////

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
