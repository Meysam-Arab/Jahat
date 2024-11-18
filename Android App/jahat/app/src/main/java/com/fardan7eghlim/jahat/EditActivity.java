package com.fardan7eghlim.jahat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditActivity extends Activity {

    private Button edit_btn;
    private Spinner types;
    private static final String TAG = Utility.class.getSimpleName();
    private ProgressDialog pDialog;
    private String uud;
    private String id;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private ImageView avatar;
    private boolean userTryTochangeAva=false;
    private ChatObj chatObj;
    private TextView title_tv;
    private EditText title;
    private boolean channelWantAdd;
    private EditText link_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //fill spinner
        addItemsOnSpinner();

        //avatar
        avatar= (ImageView) findViewById(R.id.avatar);
        bitmap=AppConfig.avatar;
        AppConfig.avatar=null;

        //
        title= (EditText) findViewById(R.id.title_et);
        title_tv= (TextView) findViewById(R.id.title_tv);
        final EditText title_et= (EditText) findViewById(R.id.title_et);
        link_et= (EditText) findViewById(R.id.link_et);
        final EditText descrpt_et= (EditText) findViewById(R.id.comment_et);

        //get from intenet
        chatObj=new ChatObj();
        chatObj= (ChatObj) getIntent().getSerializableExtra("chatObj");

        //stutus
        TextView stutus= (TextView) findViewById(R.id.stutus);
        if(chatObj.isAuth()){
            stutus.setText("تایید شده است");
        }else{
            stutus.setText("در حال بررسی");
        }

        //name title_bar
        TextView name_title_bar= (TextView) findViewById(R.id.name);
        name_title_bar.setText("ویرایش: "+chatObj.getName());

        id=chatObj.getId();
        String name=chatObj.getName();
        title_et.setText(name);
        String description=chatObj.getDescription();
        descrpt_et.setText(description);
//        String isAuthenticated=extras.getString("isAuthenticated");
        String type=chatObj.getType();
        uud=chatObj.getUnique_id();
        final String link=chatObj.getLink();
        link_et.setText(link);

        types.setSelection(Integer.parseInt(type)-1);

        //btnAddNew
        edit_btn= (Button) findViewById(R.id.btnEdit02);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new Utility().isNetworkAvailable(getApplicationContext())){
                    //edit
                    if(channelWantAdd && link_et.getText().toString().contains("/")){
                        link_et.setText(link_et.getText().toString().substring(link_et.getText().toString().lastIndexOf("/")+1));
                    }
                    if(types.getSelectedItemId()!=1 || (types.getSelectedItemId()==1 && link_et.getText().toString().startsWith("https://t.me/joinchat/") && link_et.getText().toString().length()>25)) {
                        if( ((channelWantAdd && !title.getText().toString().isEmpty()) || !channelWantAdd)  && !link_et.getText().toString().isEmpty() && !descrpt_et.getText().toString().isEmpty()){
                            if(title.getText().toString().length()>100 || link_et.getText().toString().length()>100  || descrpt_et.getText().toString().length()>500)
                                Toast.makeText(getApplicationContext(), "مقادیر ورودی بیش از حد مجاز است!!!", Toast.LENGTH_SHORT).show();
                            else
                                EditLink(title_et.getText().toString(),link_et.getText().toString().startsWith("@")?link_et.getText().toString().substring(1):link_et.getText().toString(),descrpt_et.getText().toString());
                        }else{
                            Toast.makeText(getApplicationContext(),"لطفا تمام قسمت ها را پر کنید!",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "لینک دعوت با https://t.me/joinchat شروع می شود!!!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(), "اینترنت در دسترس نیست!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //avatar
        if(bitmap!=null){
            avatar.setImageBitmap(bitmap);
        }else{
            avatar.setImageResource(R.drawable.no_avatar);
        }
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
                userTryTochangeAva=true;
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
            link_et.setHint("آدرس کانال خود را وارد کنید");
        }else{
            channelWantAdd=false;
            title.setVisibility(View.VISIBLE);
            title_tv.setVisibility(View.VISIBLE);
            if(types.getSelectedItemId()==1){
                link_et.setHint("لینک دعوت (invite link) گروه خود را وارد کنید");
            }else{
                link_et.setHint("آدرس ربات خود را وارد کنید");
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
    //edit
    private void EditLink(final String name, final String link, final String description) {
        // Tag used to cancel the request
        final String tag_string_req = "editing";
        final Context context=getApplicationContext();

        pDialog.setMessage("در حال ویرایش...");
        showDialog();

        final StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_editChannel, new Response.Listener<String>() {

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
                Toast.makeText(context,"سرعت اینترنت بسیار کم است.لطفا بعدا دوباره تلاش کنید.",Toast.LENGTH_LONG).show();
                //Log.e(TAG, "Error: " + error.getMessage());
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params
                Map<String, String> params = new HashMap<String, String>();
                params.put("edit", "1");
                params.put("jwTw", "1");
                params.put("id", id);
                params.put("unique_id", uud);
                params.put("name", name);
                params.put("description", description);
                params.put("link", link);
                params.put("type", Long.toString((types.getSelectedItemId()+1)));
                if(bitmap!=null && userTryTochangeAva) params.put("image", getStringImage(bitmap));

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
    //convert String to bitmap
    public Bitmap getStringImage(String temp){
        byte[] decodedString = Base64.decode(temp, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
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
}
