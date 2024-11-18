package com.fardan7eghlim.jahat;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends Activity {
    private Dialog d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ImageView f7e_logo= (ImageView) findViewById(R.id.f7e_logo);
        f7e_logo.setImageResource(R.drawable.f7e_logo);
        Button btn= (Button) findViewById(R.id.btnHelper02);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new Dialog(AboutUsActivity.this);
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
                    }
                });
            }
        });

        TextView tv_version = (TextView) findViewById(R.id.tv_vrsion);
        PackageInfo pInfo = null;
        String version = "نسخه: 1.0";
//        int verCode = pInfo.versionCode;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = "نسخه: " + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv_version.setText(version);
    }
}
