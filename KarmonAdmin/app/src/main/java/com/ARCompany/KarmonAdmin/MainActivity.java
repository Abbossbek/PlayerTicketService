package com.ARCompany.KarmonAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DeviceAdminService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_add_ad, btn_users, btn_users_sort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add_ad=(Button)findViewById(R.id.btn_add_ad);
        btn_add_ad.setOnClickListener(this);
        btn_users=(Button)findViewById(R.id.btn_delete_notusers);
        btn_users.setOnClickListener(this);
        btn_users_sort=(Button)findViewById(R.id.btn_users_sort);
        btn_users_sort.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btn_add_ad){
            startActivity(new Intent(this, AddAd.class));
        }
        if(view == btn_users){
            startActivity(new Intent(this, UsersActivity.class));
        }
        if(view == btn_users_sort){
            startActivity(new Intent(this, UserSortActivity.class));
        }
    }
}
