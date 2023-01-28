package com.ARCompany.UzAdMoney;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.clans.fab.Label;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_INVITE = 1718;
    private FirebaseAuth firebaseAuth;
    private TextView textViewEmail;
    private LinearLayout linearLayout;
    private ProgressDialog progressDialog;

    private String Money, Name, Surname;

    private  FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Label label_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        switch (Values.theme) {
            case 0:
                ((androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_profile)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((RelativeLayout) findViewById(R.id.relative_profile)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((com.github.clans.fab.FloatingActionMenu) findViewById(R.id.fab_menu)).setMenuButtonColorNormalResId(R.color.colorPrimaryDark);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item2)).setColorNormalResId(R.color.colorPrimaryDark);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item3)).setColorNormalResId(R.color.colorPrimaryDark);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item4)).setColorNormalResId(R.color.colorPrimaryDark);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item5)).setColorNormalResId(R.color.colorPrimaryDark);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item6)).setColorNormalResId(R.color.colorPrimaryDark);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item7)).setColorNormalResId(R.color.colorPrimaryDark);
                break;
            case 1:
                ((androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_profile)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((RelativeLayout) findViewById(R.id.relative_profile)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((com.github.clans.fab.FloatingActionMenu) findViewById(R.id.fab_menu)).setMenuButtonColorNormalResId(R.color.colorPrimaryDarkBlue);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item2)).setColorNormalResId(R.color.colorPrimaryDarkBlue);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item3)).setColorNormalResId(R.color.colorPrimaryDarkBlue);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item4)).setColorNormalResId(R.color.colorPrimaryDarkBlue);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item5)).setColorNormalResId(R.color.colorPrimaryDarkBlue);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item6)).setColorNormalResId(R.color.colorPrimaryDarkBlue);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item7)).setColorNormalResId(R.color.colorPrimaryDarkBlue);
                break;
            case 2:
                ((androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_profile)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((RelativeLayout) findViewById(R.id.relative_profile)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((com.github.clans.fab.FloatingActionMenu) findViewById(R.id.fab_menu)).setMenuButtonColorNormalResId(R.color.colorPrimaryDarkBlack);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item2)).setColorNormalResId(R.color.colorPrimaryDarkBlack);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item3)).setColorNormalResId(R.color.colorPrimaryDarkBlack);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item4)).setColorNormalResId(R.color.colorPrimaryDarkBlack);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item5)).setColorNormalResId(R.color.colorPrimaryDarkBlack);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item6)).setColorNormalResId(R.color.colorPrimaryDarkBlack);
                ((com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_menu_item7)).setColorNormalResId(R.color.colorPrimaryDarkBlack);
                break;
        }

        Values.ad_names.clear();

        progressDialog = new ProgressDialog(this);
        label_update=(Label)findViewById(R.id.label_update);
        label_update.setOnClickListener(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        textViewEmail = (TextView) findViewById(R.id.textviewUserEmail);
        linearLayout = (LinearLayout) findViewById(R.id.liner_layout);

        firebaseUser = firebaseAuth.getCurrentUser();

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    linearLayout.removeAllViewsInLayout();
                    Values.ad_names.clear();
                    try {
                        Money = dataSnapshot.child("Money").getValue().toString();
                        Name = dataSnapshot.child("Name").getValue().toString();
                        Surname = dataSnapshot.child("Surname").getValue().toString();

                        setDynamicLinkListener();

                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        String month=String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
                        String day=String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                        String hour=String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                        String minut=String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
                        String secund=String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
                        if(day.length()!=2){
                            day="0"+day;
                        }
                        if(month.length()!=2){
                            month="0"+month;
                        }
                        if(hour.length()!=2){
                            hour="0"+hour;
                        }
                        if(minut.length()!=2){
                            minut="0"+minut;
                        }
                        if(secund.length()!=2){
                            secund="0"+secund;
                        }

                        String date_time=year+"_"+month+"_"+day+"_"+hour+"_"+minut+"_"+secund;
                        Map<String, Object> mHashmap=new HashMap<>();
                        mHashmap.put("LastVisit", date_time);
                        FirebaseDatabase.getInstance().getReference()
                                .child(firebaseUser.getUid())
                                .updateChildren(mHashmap);

                        FirebaseDatabase.getInstance().getReference()
                                .child(firebaseUser.getUid()).child("Ads")
                                .addChildEventListener(new ChildEventListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                       if (!Values.ad_names.contains(dataSnapshot.getKey())) {
                                            Values.ad_names.add(0, dataSnapshot.getKey());
                                            Object ad = dataSnapshot.getValue(Object.class);
                                            ad_view_show((HashMap) ad);
                                        }

                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        if (!Values.ad_names.contains(dataSnapshot.getKey())) {
                                            Values.ad_names.add(0, dataSnapshot.getKey());
                                            Object ad = dataSnapshot.getValue(Object.class);
                                            ad_view_show((HashMap) ad);
                                        }
                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        if (!Values.ad_names.contains(dataSnapshot.getKey())) {
                                            Values.ad_names.add(0, dataSnapshot.getKey());
                                            Object ad = dataSnapshot.getValue(Object.class);
                                            ad_view_show((HashMap) ad);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }

                                });

                        textViewEmail.setText("Hisobingiz: " + Money + " so'm");
                    } catch (Exception ex) {
                        finish();
                        startActivity(new Intent(ProfileActivity.this, RegisterActivity.class));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } catch (Exception ex) {
            finish();
            startActivity(new Intent(ProfileActivity.this, RegistrationEmail.class));
        }


        findViewById(R.id.toolbar_profile).setOnClickListener(this);
        findViewById(R.id.fab_menu_item1).setOnClickListener(this);
        findViewById(R.id.fab_menu_item2).setOnClickListener(this);
        findViewById(R.id.fab_menu_item3).setOnClickListener(this);
        findViewById(R.id.fab_menu_item4).setOnClickListener(this);
        findViewById(R.id.fab_menu_item5).setOnClickListener(this);
        findViewById(R.id.fab_menu_item6).setOnClickListener(this);
        findViewById(R.id.fab_menu_item7).setOnClickListener(this);

    }

    private void setDynamicLinkListener() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }
                        //
                        // If the user isn't signed in and the pending Dynamic Link is
                        // an invitation, sign in the user anonymously, and record the
                        // referrer's UID.
                        //
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (deepLink != null) {
                            final String referrerUid = deepLink.toString().substring(deepLink.toString().indexOf('%') + 3);
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            ref.addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            //Get map of users in datasnapshot
                                            Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                                            for (Map.Entry<String, Object> entry : value.entrySet()) {
                                                //Get user map
                                                Map singleUser = (Map) entry.getValue();
                                                Map<String, Object> value1 = (Map<String, Object>) dataSnapshot.getValue();
                                                List<String> items = new ArrayList<>();
                                                for (Map.Entry<String, Object> entry1 : ((Map<String, Object>) singleUser).entrySet()) {
                                                    items.add(entry1.getKey());
                                                }
                                                if (entry.getKey().equals(referrerUid) && !items.contains("Invited")) {
                                                    final Integer moneyref = Integer.parseInt(singleUser.get("Money").toString());
                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child(referrerUid).child("Referals")
                                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    Map<String, Object> value2 = (Map<String, Object>) dataSnapshot.getValue();
                                                                    List<String> referals = new ArrayList<>();
                                                                    for (Map.Entry<String, Object> entry : value2.entrySet()) {
                                                                        referals.add(entry.getKey());
                                                                    }
                                                                    if (!referals.contains(firebaseUser.getUid())) {
                                                                        Map<String, Object> mHashmap = new HashMap<>();
                                                                        mHashmap.put(firebaseUser.getUid(), Surname + " " + Name);
                                                                        FirebaseDatabase.getInstance().getReference()
                                                                                .child(referrerUid).child("Referals")
                                                                                .updateChildren(mHashmap);
                                                                        mHashmap.clear();
                                                                        mHashmap.put("Money", String.valueOf(moneyref + 100));
                                                                        FirebaseDatabase.getInstance().getReference()
                                                                                .child(referrerUid)
                                                                                .updateChildren(mHashmap);
                                                                        mHashmap.clear();
                                                                        mHashmap.put("Invited", "1");
                                                                        FirebaseDatabase.getInstance().getReference()
                                                                                .child(firebaseUser.getUid())
                                                                                .updateChildren(mHashmap);
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            //handle databaseError
                                        }
                                    });
                        }
                    }
                });
    }

    private Task<ShortDynamicLink>  createlink() {
        Task<ShortDynamicLink> dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://play.google.com/store/apps/details?id=com.ARCompany.Karmon/?invitedby="+FirebaseAuth.getInstance().getCurrentUser().getUid()))
                .setDomainUriPrefix("https://karmon.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.ARCompany.Karmon")
                                .setMinimumVersion(125)
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Reklama ko'r, pul ishla")
                                .setDescription("Yuklab ol va o'z hisobingni yarat!")
                                .build())
                .buildShortDynamicLink();

         return dynamicLink;
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
                ((com.github.clans.fab.FloatingActionMenu)findViewById(R.id.fab_menu)).close(true);

                for(int i=0;i<linearLayout.getChildCount();i++){
                    if(linearLayout.getChildAt(i).equals(view)){
                        Values.ad_index=i;
                    }
                }
                Values.ad_url=view.getTag().toString();
                startActivity(new Intent(ProfileActivity.this, AdActivity.class));

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

        if(hashMap.get("clicked").toString().equals("0")){
            title.setText(title.getText()+" ("+hashMap.get("pay").toString()+" so'm)");
            linearLayout1.setBackground(getResources().getDrawable(R.drawable.card_background));
            cardView.setCardElevation(15f);
        }

        linearLayout1.addView(title);
        linearLayout1.addView(body);
        cardView.addView(linearLayout1);
        linearLayout.addView(cardView,0);

    }
    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.toolbar_profile)){
            ((com.github.clans.fab.FloatingActionMenu)findViewById(R.id.fab_menu)).close(true);

        }
        if(view == findViewById(R.id.fab_menu_item1)){
            Values.signed_in=false;
            Values.passed=false;
            Values.password_changed=false;
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if(view == findViewById(R.id.fab_menu_item4)){
            startActivity(new Intent(this, InformationsActivity.class));
        }
        if(view == findViewById(R.id.fab_menu_item2)){
            startActivity(new Intent(this, MoneyOutActivity.class));
        }
        if(view == findViewById(R.id.fab_menu_item3)){
            startActivity(new Intent(this, AddAdActivity.class));
        }
        if(view == findViewById(R.id.fab_menu_item5)){
            progressDialog.setMessage("Ulashishga tayyorlanmoqda...");
            progressDialog.show();
            createlink().addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                @Override
                public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Intent intent=new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, task.getResult().getShortLink().toString());
                        intent.setType("text/plain");
                        startActivity(intent);
                    }
                }
            });
        }
        if(view == findViewById(R.id.fab_menu_item6)){
            finish();
            startActivity(new Intent(ProfileActivity.this, SettingActivity.class));
        }
        if(view == findViewById(R.id.fab_menu_item7)){
            startActivity(new Intent(ProfileActivity.this, MyAdsActivity.class));
        }
        if(view==label_update){
            this.recreate();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d("Invites", "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }

}
