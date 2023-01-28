package com.ARCompany.UzAdMoney;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InformationsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private TextView text_invited_userd;
    List<String> referals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);

        switch (Values.theme){
            case 0:
                ((Toolbar)findViewById(R.id.toolbar_informations)).setBackground(getResources().getDrawable(R.color.colorPrimary));
                ((RelativeLayout)findViewById(R.id.relative_informations)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                break;
            case 1:
                ((Toolbar)findViewById(R.id.toolbar_informations)).setBackground(getResources().getDrawable(R.color.colorPrimaryBlue));
                ((RelativeLayout)findViewById(R.id.relative_informations)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                break;
            case 2:
                ((Toolbar)findViewById(R.id.toolbar_informations)).setBackground(getResources().getDrawable(R.color.colorPrimaryBlack));
                ((RelativeLayout)findViewById(R.id.relative_informations)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                break;
        }

        referals = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.child("Name").getValue().toString();
                String Surname = dataSnapshot.child("Surname").getValue().toString();
                String Sex = dataSnapshot.child("Sex").getValue().toString().equals("1")?"Erkak":"Ayol";
                String Region = dataSnapshot.child("Region").getValue().toString();
                String Profession = dataSnapshot.child("Profession").getValue().toString();
                String Date = dataSnapshot.child("Date").getValue().toString();
                String Phone = firebaseUser.getPhoneNumber();

                ((TextView) findViewById(R.id.textFullName)).setText(Surname + " " + Name);
                ((TextView) findViewById(R.id.textSex)).setText(Sex);
                ((TextView) findViewById(R.id.textRegion)).setText(Region);
                ((TextView) findViewById(R.id.textProfession)).setText(Profession);
                ((TextView) findViewById(R.id.textDate)).setText(Date);
                ((TextView) findViewById(R.id.textPhone)).setText(Phone);

                Map<String,Object> map=(Map<String,Object>)dataSnapshot.child("Referals").getValue();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if(!entry.getValue().toString().equals(Surname+" "+Name)) {
                        referals.add(entry.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        text_invited_userd=(TextView)findViewById(R.id.text_invited_users);
        text_invited_userd.setOnClickListener(this);

        findViewById(R.id.delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.delete)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Hisobingizni rostdan ham o'chirmoqchimisiz? Barcha ma'lumotlaringiz va hisobingizdagi pul miqdori o'chishini xohlaysizmi?")
                    .setPositiveButton("O'chir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                                //we are connected to a network

                            final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential("user@example.com", "password1234");

                            // Prompt the user to re-provide their sign-in credentials
                            firebaseUser.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            firebaseUser.delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(InformationsActivity.this, "User account deleted.", Toast.LENGTH_LONG);
                                                            }
                                                        }
                                                    });
                                            finish();

                                            startActivity(new Intent(InformationsActivity.this, MainActivity.class));
                                            databaseReference.removeValue();

                                        }
                                    });
                            }
                            else{
                                Toast.makeText(InformationsActivity.this, "Internet aloqasini tekshiring va qaytadan urinib ko'ring!", Toast.LENGTH_LONG).show();
                            }

                        }
                    })
                    .setNegativeButton("Bekor qilish", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            builder.create().show();

        }
        if(view == text_invited_userd){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Taklif qilinganlar")
                    .setItems(referals.toArray(new String[0]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            builder.create().show();

        }
    }
}