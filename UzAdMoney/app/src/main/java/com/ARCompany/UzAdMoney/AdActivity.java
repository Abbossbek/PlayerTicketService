package com.ARCompany.UzAdMoney;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.Label;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AdActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;
    private ProgressBar progressBar;
    private boolean loadingFinished = true;
    private boolean redirect = false;
    private Boolean timer_stopped=false, connected=true, page_loaded=false;
    private Label label_timer;
    private Integer Money, pay, clicked=0;
    private DatabaseReference databaseReferenceM;
    private CountDownTimer timer;
    private Button btn_ad;
    private String ad_url_site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        btn_ad=(Button)findViewById(R.id.btn_ad);
        btn_ad.setOnClickListener(this);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Ads")
                .child(Values.ad_names.get(Values.ad_index));

        databaseReferenceM = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReferenceM.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Money=Integer.parseInt(dataSnapshot.child("Money").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pay = Integer.parseInt(dataSnapshot.child("pay").getValue().toString());
                Money += pay;
                try {
                    ad_url_site = dataSnapshot.child("ad_url_site").getValue().toString();
                    if(!(ad_url_site.equals(null)||ad_url_site.equals(""))) {
                        btn_ad.setVisibility(View.VISIBLE);
                        btn_ad.bringToFront();
                    }
                }
                catch (Exception ex){
                    btn_ad.setVisibility(View.GONE);
                }
                clicked=Integer.parseInt(dataSnapshot.child("clicked").getValue().toString());
                if(label_timer.getText()!="X") {
                    int duration=pay*2*1000;
                    timer = new CountDownTimer(duration, 1000) {

                        public void onTick(long millisUntilFinished) {
                            label_timer.setText(String.valueOf(millisUntilFinished / 1000));
                        }

                        public void onFinish() {
                            if (connected && page_loaded) {
                                if (clicked == 0) {
                                    Map<String, Object> mHashmap1 = new HashMap<>();
                                    mHashmap1.put("clicked", "1");
                                    databaseReference.updateChildren(mHashmap1);
                                    Map<String, Object> mHashmap2 = new HashMap<>();
                                    mHashmap2.put("Money", String.valueOf(Money));
                                    databaseReferenceM.updateChildren(mHashmap2);

                                }
                            }
                            label_timer.setText("X");
                            timer_stopped = true;

                        }
                    }.start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        progressBar=(ProgressBar)findViewById(R.id.progress_bar_ad_view);
        progressBar.bringToFront();

        label_timer=(Label)findViewById(R.id.label_timer);
        label_timer.setOnClickListener(this);
        label_timer.bringToFront();

        webView=(WebView)findViewById(R.id.ad_web_view);
        webView.loadUrl(Values.ad_url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                if (!loadingFinished) {
                    redirect = true;
                }
                loadingFinished = false;
                webView.loadUrl(urlNewString);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap bitmap) {
                loadingFinished = false;
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                page_loaded=true;
                timer.cancel();
                if (!redirect) {
                    loadingFinished = true;
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    redirect = false;
                }
                if(connected&&clicked==0){
                timer.start();
                }
                else {
                    label_timer.setText("X");
                    timer_stopped=true;
                }

            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // Do something
                webView.setVisibility(View.INVISIBLE);
                ((TextView)findViewById(R.id.text_no_connection)).bringToFront();
                connected=false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(timer_stopped || clicked==1){
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        if(view==label_timer&&timer_stopped){
            finish();
        }
        if(view == btn_ad){
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(ad_url_site));
            startActivity(intent);
        }
    }
}
