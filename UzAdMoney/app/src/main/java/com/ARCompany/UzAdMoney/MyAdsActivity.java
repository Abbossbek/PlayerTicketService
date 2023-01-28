package com.ARCompany.UzAdMoney;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdsActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linearLayout;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private List<String> my_ad_names=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);

        firebaseAuth = FirebaseAuth.getInstance();
        linearLayout = (LinearLayout) findViewById(R.id.liner_layout_may_ads);
        progressDialog =new ProgressDialog(this);

        try {
            findViewById(R.id.update_menu).setOnClickListener(this);
        }
        catch (Exception ex){
            Toast.makeText(MyAdsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    linearLayout.removeAllViewsInLayout();
                    Values.my_ads.clear();
                    try {

                        FirebaseDatabase.getInstance().getReference()
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("My_ads")
                                .addChildEventListener(new ChildEventListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        if (!Values.my_ads.contains(dataSnapshot.child("title").toString())) {
                                            Values.my_ads.add(0, dataSnapshot.child("title").toString());
                                            Object ad = dataSnapshot.getValue(Object.class);
                                            ad_view_show((HashMap) ad);
                                        }
                                        else {
                                            progressDialog.dismiss();
                                        }

                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        if (!Values.my_ads.contains(dataSnapshot.child("title").toString())) {
                                            Values.my_ads.add(0, dataSnapshot.child("title").toString());
                                            Object ad = dataSnapshot.getValue(Object.class);
                                            ad_view_show((HashMap) ad);
                                        }
                                        else {
                                            progressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        if (!Values.my_ads.contains(dataSnapshot.child("title").toString())) {
                                            Values.my_ads.add(0, dataSnapshot.child("title").toString());
                                            Object ad = dataSnapshot.getValue(Object.class);
                                            ad_view_show((HashMap) ad);
                                        }
                                        else {
                                            progressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }

                                });

                    } catch (Exception ex) {
                        Toast.makeText(MyAdsActivity.this, "Xatolik yuz berdi!", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } catch (Exception ex) {
            Toast.makeText(MyAdsActivity.this, "Xatolik yuz berdi!", Toast.LENGTH_LONG).show();
        }

    }
    private void ad_view_show(final HashMap hashMap){

        LinearLayout.LayoutParams params = new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        CardView cardView = new CardView(getApplicationContext());
        cardView.setLayoutParams(params);
        cardView.setRadius(15.0f);
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
        layoutParams.setMargins(0,5,0,5);
        cardView.requestLayout();
        cardView.setUseCompatPadding(true);
        cardView.setTag(hashMap.get("url").toString());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0;i<linearLayout.getChildCount();i++){
                    if(linearLayout.getChildAt(i).equals(view)){
                        Values.ad_index=i;
                    }
                }
                Values.ad_url=view.getTag().toString();
                startActivity(new Intent(MyAdsActivity.this, AdActivity.class));

            }
        });

        LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
        linearLayout1.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        switch (Values.theme){
            case 0:
                linearLayout1.setBackground(getResources().getDrawable(R.drawable.cliced_card));
                break;
            case 1:
                linearLayout1.setBackground(getResources().getDrawable(R.drawable.cliced_card_blue));
                break;
            case 2:
                linearLayout1.setBackground(getResources().getDrawable(R.drawable.cliced_card_black));
                break;
        }
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        linearLayout1.setPadding(20,10,10,10);

        TextView title = new TextView(getApplicationContext());
        title.setLayoutParams(params);
        title.setTextColor(Color.WHITE);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        title.setText(hashMap.get("title").toString());

        TextView body = new TextView(getApplicationContext());
        body.setLayoutParams(params);
        body.setTextColor(Color.WHITE);
        body.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        body.setText(hashMap.get("body").toString());

        title.setText(title.getText()+" ("+hashMap.get("clicked").toString()+" ta odam ko'rdi)");

        linearLayout1.addView(title);
        linearLayout1.addView(body);
        cardView.addView(linearLayout1);
        linearLayout.addView(cardView,0);

    }

    @Override
    public void onClick(View view) {
        progressDialog.setMessage("Hisoblanmoqda...");
        progressDialog.show();

        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference();
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(String ad_title:Values.my_ads) {
                    int count=0;
                    String key="", click="1";
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        if(user.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            for (DataSnapshot ad : user.child("My_ads").getChildren()) {
                                if (ad.child("title").toString().equals(ad_title)) {
                                    key = ad.getKey();
                                }
                            }
                        }
                        for (DataSnapshot ad : user.child("Ads").getChildren()) {
                            String title = ad.child("title").toString(), clicked=ad.child("clicked").getValue().toString();
                            if (ad_title.equals(title)) {
                                if(click.equals(clicked))
                                    count++;
                            }

                        }
                    }
                    Map<String, Object> mHashmap = new HashMap<>();
                    mHashmap.put("clicked",String.valueOf(count));
                    FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser()
                            .getUid()).child("My_ads").child(key).updateChildren(mHashmap);

                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
