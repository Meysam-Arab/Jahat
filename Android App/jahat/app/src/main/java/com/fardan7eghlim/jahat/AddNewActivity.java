package com.fardan7eghlim.jahat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rogerlemmonapps.captcha.Captcha;
import com.rogerlemmonapps.captcha.MathCaptcha;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

public class AddNewActivity extends Activity {

    private Button add_btn;
    private Spinner types;
    private static final String TAG = Utility.class.getSimpleName();
    private ProgressDialog pDialog;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private ImageView avatar;
    private TextView title_tv;
    private EditText title;
    private boolean channelWantAdd;
    private EditText link;
    private Captcha captcha;
    private AsyncTask<String, Integer, Boolean> asyncTask;
    private boolean addedInfo=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        title= (EditText) findViewById(R.id.title_et);
        title_tv= (TextView) findViewById(R.id.title_tv);
        link= (EditText) findViewById(R.id.link_et);
        final EditText descrpt= (EditText) findViewById(R.id.comment_et);
        final EditText email= (EditText) findViewById(R.id.email_et);
        avatar= (ImageView) findViewById(R.id.avatar);

        bitmap=null;
        avatar.setImageResource(R.drawable.no_avatar);
        ///captcha
        final ImageView captcha_img = (ImageView)findViewById(R.id.captcha_img);
        Button captcha_btn = (Button)findViewById(R.id.captcha_btn);
        final EditText answer_et= (EditText) findViewById(R.id.captcha_answer_et);

        captcha =  new MathCaptcha(300, 100, MathCaptcha.MathOptions.PLUS_MINUS_MULTIPLY);
        captcha_img.setImageBitmap(captcha.getImage());
        captcha_img.setLayoutParams(new LinearLayout.LayoutParams((int)(captcha.width*(1)) , (int)(captcha.height*1) ));

        captcha_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                captcha =  new MathCaptcha(300, 100, MathCaptcha.MathOptions.PLUS_MINUS_MULTIPLY);
                captcha_img.setImageBitmap(captcha.getImage());
                captcha_img.setLayoutParams(new LinearLayout.LayoutParams((int)(captcha.width*(1)) , (int)(captcha.height*1) ));
                answer_et.setText("");
            }
        });
        //get Email
        final SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String email_st = mPrefs.getString("email_spt", "00");
        if(!email_st.equals("00")){
            email.setText(email_st);
        }
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {}
            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {}
            @Override
            public void afterTextChanged(Editable arg0) {
                //save last email was typed
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("email_spt", email.getText().toString()).commit();
            }
        });

        //fill spinner
        addItemsOnSpinner();
        handelNameShowing();

        //btnAddNew
        add_btn= (Button) findViewById(R.id.btnAddNew);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(new Utility().isNetworkAvailable(getApplicationContext()))) {
                    Toast.makeText(getBaseContext(), "اینترنت در دسترس نیست!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(channelWantAdd && link.getText().toString().contains("/")){
                    link.setText(link.getText().toString().substring(link.getText().toString().lastIndexOf("/")+1));
                }
                //add
                LinearLayout linearLayout= (LinearLayout) findViewById(R.id.captcha_layout);
                if(captcha.checkAnswer(answer_et.getText().toString()) || true) {
                    if (Email(email.getText().toString())) {
                        if (types.getSelectedItemId() != 1 || (types.getSelectedItemId() == 1 && link.getText().toString().startsWith("https://t.me/joinchat/") && link.getText().toString().length() > 25)) {
                            if (((!channelWantAdd && !title.getText().toString().isEmpty()) || channelWantAdd) && !link.getText().toString().isEmpty() && !email.getText().toString().isEmpty()) {
                                if(title.getText().toString().length()>100 || link.getText().toString().length()>100 || email.getText().toString().length()>100 || descrpt.getText().toString().length()>500)
                                    Toast.makeText(getApplicationContext(), "مقادیر ورودی بیش از حد مجاز است!!!", Toast.LENGTH_SHORT).show();
                                else
                                    AddLink(title.getText().toString(), email.getText().toString(), link.getText().toString().startsWith("@") ? link.getText().toString().substring(1) : link.getText().toString(), descrpt.getText().toString());
                            } else {
                                Toast.makeText(getApplicationContext(), "لطفا تمام قسمت ها را پر کنید!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "لینک دعوت با https://t.me/joinchat شروع می شود!!!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "لطفا ایمیل معتبر وارد کنید.", Toast.LENGTH_SHORT).show();
                    }
                }else
                    {
                        linearLayout.setBackgroundColor(Color.parseColor("#8BE77523"));
                    }
            }
        });
        //avatar changer
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        //spinner
        types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                handelNameShowing();
                descrpt.setText("");
                addedInfo=false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                handelNameShowing();
            }

        });

        //warning
        ImageView warning= (ImageView) findViewById(R.id.warning_img);
        warning.setImageResource(R.drawable.warning);

        //description
//        if(!addedInfo && channelWantAdd){
//        link.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    // code to execute when EditText loses focus
//                    //get about channel
//                    final String link_str=link.getText().toString();
//                    asyncTask=new AsyncTask<String, Integer, Boolean>() {
//                        @Override
//                        protected Boolean doInBackground(String... params) {
//                            new Utility().getInfoOFChannel(new CallBack() {
//                                @Override
//                                public void onSuccess(String info) {
//                                    // Do nothing
//                                    if(info!=null){
//                                        EditText descrpt= (EditText) findViewById(R.id.comment_et);
//                                        descrpt.setText(descrpt.getText().toString()+"\n"+info);
//                                        addedInfo=true;
//                                    }
//                                }
//                                @Override
//                                public void onFail() {
//                                    // Do Stuff
//                                }
//                            },link_str);
//                            return true;
//                        }
//                        @Override
//                        protected void onPostExecute(Boolean aBoolean) {
//                            super.onPostExecute(aBoolean);
//                        }
//                    };
//
//                    asyncTask.execute();
//                }
//            }
//        });}
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                avatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //handle to show name or not
    private void handelNameShowing(){
        if(types.getSelectedItemId()==0){
            channelWantAdd=true;
            title.setVisibility(View.GONE);
            title_tv.setVisibility(View.GONE);
            link.setHint("آدرس کانال خود را وارد کنید");
        }else{
            channelWantAdd=false;
            title.setVisibility(View.VISIBLE);
            title_tv.setVisibility(View.VISIBLE);
            if(types.getSelectedItemId()==1){
                link.setHint("لینک دعوت (invite link) گروه خود را وارد کنید");
            }else{
                link.setHint("آدرس ربات خود را وارد کنید");
            }
        }
    }
    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        types = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("کانال (channel)");
        list.add("گروه عمومی (public group)");
        list.add("ربات (bot)");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_types,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(dataAdapter);
    }
    //add link
    private void AddLink(final String name, final String email, final String link, final String description) {
        // Tag used to cancel the request
        final String tag_string_req = "add_new_Link";
        final Context context=getApplicationContext();

        pDialog.setMessage("در حال اضافه کردن...");
        showDialog();

        final StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_addChannel, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // link successfully stored in MySQL
                        // Launch Main activity
                        Toast.makeText(context,jObj.getString("mess"),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error occurred in adding link. Get the error
                        // message
                        Toast.makeText(context,
                                jObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context,"سرعت اینترنت بسیار کم است.لطفا بعدا دوباره تلاش کنید.",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(context,"سرعت اینترنت بسیار کم است.لطفا بعدا دوباره تلاش کنید.",Toast.LENGTH_LONG).show();
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params
                Map<String, String> params = new HashMap<String, String>();
                params.put("addNew", "1");
                params.put("jwTw", "1");
//                params.put("score", "0");
                params.put("name", name);
                if(!description.isEmpty()) params.put("description", description);
                params.put("link", link.trim());
                params.put("type", Long.toString((types.getSelectedItemId()+1)));
                params.put("email", email);
                if(bitmap!=null) params.put("image", getStringImage(bitmap));

                return params;
            }
        };
        strReq.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 120000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 120000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
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
    //pattern of email
    public boolean Email(String email)
    {
        return  (email.length()>12)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    ///show image picker
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    ///convert bitmap to string
    public String getStringImage(Bitmap bmp){
        double z=100.0/(double)bmp.getWidth();
        bmp=FileProcessor.getResizedBitmap(bmp,(int)(bmp.getWidth()*z), (int)(bmp.getHeight()*z));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    /////
    public interface CallBack {
        void onSuccess(String info);
        void onFail();
    }
}
