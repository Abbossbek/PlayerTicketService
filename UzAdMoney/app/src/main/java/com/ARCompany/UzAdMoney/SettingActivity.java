package com.ARCompany.UzAdMoney;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean theme_changed=false;
    private Spinner spinner_themes;
    private TextView text_about, text_change_password, text_change_informations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        switch (Values.theme){
            case 0:
                ((Toolbar)findViewById(R.id.toolbar_setting)).setBackground(getResources().getDrawable(R.color.colorPrimary));
                ((RelativeLayout)findViewById(R.id.relative_setting)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                break;
            case 1:
                ((Toolbar)findViewById(R.id.toolbar_setting)).setBackground(getResources().getDrawable(R.color.colorPrimaryBlue));
                ((RelativeLayout)findViewById(R.id.relative_setting)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                break;
            case 2:
                ((Toolbar)findViewById(R.id.toolbar_setting)).setBackground(getResources().getDrawable(R.color.colorPrimaryBlack));
                ((RelativeLayout)findViewById(R.id.relative_setting)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                break;
        }

        text_about=(TextView)findViewById(R.id.text_about);
        text_about.setOnClickListener(this);
        text_change_password=(TextView)findViewById(R.id.text_change_password);
        text_change_password.setOnClickListener(this);
        text_change_informations=(TextView)findViewById(R.id.text_change_informations);
        text_change_informations.setOnClickListener(this);

        spinner_themes=(Spinner)findViewById(R.id.spinner_themes);
        spinner_themes.setSelection(Values.theme);
        spinner_themes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=Values.theme) {
                    theme_changed=true;
                    switch (i) {
                        case 0:
                            Values.theme = 0;
                            Values.theme_chanched=true;
                            finish();
                            startActivity(new Intent(SettingActivity.this, MainActivity.class));
                            break;
                        case 1:
                            Values.theme = 1;
                            Values.theme_chanched=true;
                            finish();
                            startActivity(new Intent(SettingActivity.this, MainActivity.class));
                            break;
                        case 2:
                            Values.theme = 2;
                            Values.theme_chanched=true;
                            finish();
                            startActivity(new Intent(SettingActivity.this, MainActivity.class));
                            break;

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(theme_changed) {
            super.onBackPressed();
        }
        else {
            finish();
            startActivity(new Intent(SettingActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        if(view == text_about){
            startActivity(new Intent(SettingActivity.this, AboutActivity.class));
        }
        if(view == text_change_password){
            Values.passed=false;
            Values.signed_in=false;
            startActivity(new Intent(SettingActivity.this, PasswordActivity.class));
        }
        if(view == text_change_informations){
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                Values.informations_change = true;
                startActivity(new Intent(SettingActivity.this, RegisterActivity.class));
            }
            else {
                Toast.makeText(SettingActivity.this, "Internet aloqasini tekshiring va qaytadan urinib ko'ring!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
