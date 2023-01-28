package com.ARCompany.UzAdMoney;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        switch (Values.theme){
            case 0:
                ((RelativeLayout)findViewById(R.id.relative_about)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((Button)findViewById(R.id.btn_channel)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                break;
            case 1:
                ((RelativeLayout)findViewById(R.id.relative_about)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((Button)findViewById(R.id.btn_channel)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                break;
            case 2:
                ((RelativeLayout)findViewById(R.id.relative_about)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((Button)findViewById(R.id.btn_channel)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                break;
        }

        ((Button) findViewById(R.id.btn_channel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://t.me/KarmonUz"));
                startActivity(intent);
            }
        });
    }
}

