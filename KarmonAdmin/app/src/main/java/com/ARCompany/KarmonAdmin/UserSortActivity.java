package com.ARCompany.KarmonAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UserSortActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private List<List<String>> referals_list;
    private ProgressDialog progressDialog;
    private Button btn_sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sort);

        btn_sort = (Button) findViewById(R.id.btn_sort);
        btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] array=new int[linearLayout.getChildCount()];
                int low = 0, high = linearLayout.getChildCount()-1;
                for(int i=0;i<linearLayout.getChildCount();i++){
                    array[i] = Integer.parseInt(linearLayout.getChildAt(i).getTag().toString().split("#")[1]);
                }

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Yuklanmoqda...");
        progressDialog.show();

        linearLayout = (LinearLayout) findViewById(R.id.linear_users_sort);
        referals_list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference()
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (!dataSnapshot.getKey().equals("Users")) {
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
        layoutParams.setMargins(0, 5, 0, 5);
        cardView.requestLayout();
        cardView.setUseCompatPadding(true);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(UserSortActivity.this);
                builder.setTitle("Taklif qilinganlar")
                        .setItems(referals_list.get(Integer.parseInt(view.getTag().toString().split("#")[0])).toArray(new String[0]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create().show();
            }
        });

        TextView user = new TextView(getApplicationContext());
        user.setLayoutParams(params);
        user.setTextColor(Color.WHITE);
        user.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        user.setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
        String s = key + '\n' +
                "Surname: " + hashMap.get("Surname").toString() + '\n' +
                "Name: " + hashMap.get("Name").toString() + '\n' +
                "Money: " + hashMap.get("Money").toString() + '\n' +
                "Phone: " + hashMap.get("Phone").toString() + '\n' +
                "Sex: " + hashMap.get("Sex").toString() + '\n' +
                "Region: " + hashMap.get("Region").toString() + '\n' +
                "Profession: " + hashMap.get("Profession").toString() + '\n' +
                "Date: " + hashMap.get("Date").toString();

        String Name = dataSnapshot.child("Name").getValue().toString();
        String Surname = dataSnapshot.child("Surname").getValue().toString();
        List<String> referals = new ArrayList<>();
        try {
            s += "\nLastVisit: " + hashMap.get("LastVisit").toString();


        } catch (Exception ex) {
        }
        try {
            Map<String, Object> map = (Map<String, Object>) dataSnapshot.child("Referals").getValue();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (!entry.getValue().toString().equals(Surname + " " + Name)) {
                    referals.add(entry.getValue().toString());
                }
            }
        } catch (Exception ex) {
            Toast.makeText(UserSortActivity.this, s, Toast.LENGTH_LONG);
        }
        s += "\nReferals: " + referals.size();
        referals_list.add(referals);
        user.setText(s);
        cardView.setTag(String.valueOf(referals_list.size() - 1) + "#" + String.valueOf(referals.size()));
        user.setPadding(20, 10, 10, 10);

        cardView.addView(user);
        if(linearLayout.getChildCount()>1) {
            for (int i = 1; i < linearLayout.getChildCount(); i++) {
                if(referals.size() >= Integer.parseInt(linearLayout.getChildAt(0).getTag().toString().split("#")[1])){
                    linearLayout.addView(cardView, 0);
                    break;
                }
                if(referals.size() <= Integer.parseInt(linearLayout.getChildAt(linearLayout.getChildCount()-1).getTag().toString().split("#")[1])){
                    linearLayout.addView(cardView);
                    break;
                }

                if (referals.size() <= Integer.parseInt(linearLayout.getChildAt(i - 1).getTag().toString().split("#")[1])
                        && referals.size() >= Integer.parseInt(linearLayout.getChildAt(i).getTag().toString().split("#")[1])) {
                    linearLayout.addView(cardView, i);
                    break;
                }
            }
        }
        else {
            if(linearLayout.getChildCount()==1 && referals.size() <= Integer.parseInt(linearLayout.getChildAt(0).getTag().toString().split("#")[1])) {
                linearLayout.addView(cardView);
            }
            else {
                linearLayout.addView(cardView, 0);
            }
        }

    }
}
