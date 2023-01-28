package com.ARCompany.KarmonAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private EditText editText_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        linearLayout = (LinearLayout) findViewById(R.id.linear_users);
        editText_search=(EditText)findViewById(R.id.editText_search);
        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                for(int j=0;j<linearLayout.getChildCount();j++){
                    linearLayout.getChildAt(j).setVisibility(View.VISIBLE);
                }

                for(int j=0;j<linearLayout.getChildCount();j++){
                    String s=linearLayout.getChildAt(j).getTag().toString();
                    if(!s.contains(editText_search.getText().toString())){
                        linearLayout.getChildAt(j).setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        FirebaseDatabase.getInstance().getReference()
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if(!dataSnapshot.getKey().equals("Users")) {
                            Object user = dataSnapshot.getValue(Object.class);
                            user_view((HashMap) user, dataSnapshot.getKey(), dataSnapshot);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }

                });

    }

    private void user_view(HashMap hashMap, String key, DataSnapshot dataSnapshot) {
        LinearLayout.LayoutParams params = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        CardView cardView = new CardView(getApplicationContext());
        cardView.setLayoutParams(params);
        cardView.setRadius(15.0f);
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
        layoutParams.setMargins(0,5,0,5);
        cardView.requestLayout();
        cardView.setUseCompatPadding(true);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Values.UId=view.getTag().toString().substring(0,view.getTag().toString().indexOf('\n'));

                startActivity(new Intent(UsersActivity.this, ChangeUserActivity.class));
            }
        });

        TextView user = new TextView(getApplicationContext());
        user.setLayoutParams(params);
        user.setTextColor(Color.WHITE);
        user.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        user.setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
        String s=key +'\n'+
                "Surname: " + hashMap.get("Surname").toString()+'\n'+
                "Name: " + hashMap.get("Name").toString()+'\n'+
                "Money: " + hashMap.get("Money").toString()+'\n'+
                "Phone: " + hashMap.get("Phone").toString()+'\n'+
                "Sex: " + hashMap.get("Sex").toString()+'\n'+
                "Region: " + hashMap.get("Region").toString()+'\n'+
                "Profession: " + hashMap.get("Profession").toString()+'\n'+
                "Date: " + hashMap.get("Date").toString();
        try{
            s += "\nLastVisit: " + hashMap.get("LastVisit").toString();
        }
        catch (Exception ex){

        }

        String Name = dataSnapshot.child("Name").getValue().toString();
        String Surname = dataSnapshot.child("Surname").getValue().toString();
        List<String> referals = new ArrayList<>();
        try {
            Map<String, Object> map = (Map<String, Object>) dataSnapshot.child("Referals").getValue();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (!entry.getValue().toString().equals(Surname + " " + Name)) {
                    referals.add(entry.getValue().toString());
                }
            }
        } catch (Exception ex) {
            Toast.makeText(UsersActivity.this, s, Toast.LENGTH_LONG);
        }
        s += "\nReferals: " + referals.size();
        user.setText(s);
        cardView.setTag(s);
        user.setPadding(20,10,10,10);

        cardView.addView(user);
        linearLayout.addView(cardView,0);

    }
}
