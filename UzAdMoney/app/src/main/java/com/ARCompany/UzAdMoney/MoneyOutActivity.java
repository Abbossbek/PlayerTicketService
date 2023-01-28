package com.ARCompany.UzAdMoney;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class MoneyOutActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView btn_money_out_phone,btn_money_out_card,btn_money_out_account;
    private ProgressDialog progressDialog;
    private Integer Money;
    private FirebaseAuth firebaseAuth,firebaseAuthTemp;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser,firebaseUserTemp;
    private Boolean phone_clicked=false, card_clicked=false, account_clicked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_out);

        switch (Values.theme){
            case 0:
                ((LinearLayout)findViewById(R.id.line_money_out1)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((LinearLayout)findViewById(R.id.line_money_out2)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((LinearLayout)findViewById(R.id.line_money_out3)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((RelativeLayout)findViewById(R.id.relative_money_out)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.btn_money_out_phone_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.btn_money_out_account_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.btn_money_out_card_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                break;
            case 1:
                ((LinearLayout)findViewById(R.id.line_money_out1)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((LinearLayout)findViewById(R.id.line_money_out2)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((LinearLayout)findViewById(R.id.line_money_out3)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((RelativeLayout)findViewById(R.id.relative_money_out)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.btn_money_out_phone_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.btn_money_out_account_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.btn_money_out_card_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                break;
            case 2:
                ((LinearLayout)findViewById(R.id.line_money_out1)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((LinearLayout)findViewById(R.id.line_money_out2)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((LinearLayout)findViewById(R.id.line_money_out3)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((RelativeLayout)findViewById(R.id.relative_money_out)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.btn_money_out_phone_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.btn_money_out_account_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.btn_money_out_card_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                break;
        }

        progressDialog=new ProgressDialog(this);

        btn_money_out_phone=(CardView)findViewById(R.id.btn_money_out_phone);
        btn_money_out_card=(CardView)findViewById(R.id.btn_money_out_card);
        btn_money_out_account=(CardView)findViewById(R.id.btn_money_out_account);
        btn_money_out_phone.setOnClickListener(this);
        btn_money_out_card.setOnClickListener(this);
        btn_money_out_account.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Money=Integer.parseInt(dataSnapshot.child("Money").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == btn_money_out_phone) {
            phone_clicked=true;
            card_clicked=false;
            account_clicked=false;
            if (!TextUtils.isEmpty(((EditText) findViewById(R.id.edittextPhone)).getText()) &&
                    !TextUtils.isEmpty(((EditText) findViewById(R.id.edittextPhoneMoney)).getText()) &&
                    Integer.parseInt(((EditText) findViewById(R.id.edittextPhoneMoney)).getText().toString()) >= 1000) {
                if ( Money > Integer.parseInt(((EditText) findViewById(R.id.edittextPhoneMoney)).getText().toString())) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MoneyOutActivity.this)
                            .setMessage("Hisobingizdan chiqarmoqchi bo'lgan pul miqdori 1 sutka ichida telefon raqamingizga o'tkazib beriladi. " +
                                    "Iltimos pul telefoningizga tushmaguniga qadar boshqa pul chiqarish amaliyotini bajarmang!")
                            .setPositiveButton("O'tkazish", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    FileOutputStream fileout = null;
                                    try {
                                        fileout = openFileOutput("money_out_phone.krm", MODE_PRIVATE);
                                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileout));
                                        bufferedWriter.write("money: " + ((EditText) findViewById(R.id.edittextPhoneMoney)).getText().toString());
                                        bufferedWriter.append("\r\ntel: " + ((EditText) findViewById(R.id.edittextPhone)).getText().toString());

                                        bufferedWriter.close();
                                        fileout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    uploadFile("/data/user/0/com.ARCompany.Karmon/files/money_out_phone.krm");
                                    progressDialog.setMessage("Ma'lumotlar yuborilmoqda...");
                                    progressDialog.show();
                                }
                            })
                            .setNegativeButton("Bekor qilish", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create().show();
                } else {
                    Toast.makeText(MoneyOutActivity.this, "Hisobdagi pul yetarli emas!", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(MoneyOutActivity.this, "Iltimos, ma'lumotlarni to'liq va to'g'ri kiriting!", Toast.LENGTH_LONG).show();
            }
        }
        if (view == btn_money_out_card) {
            card_clicked=true;
            phone_clicked=false;
            account_clicked=false;
            if (!TextUtils.isEmpty(((EditText) findViewById(R.id.edittextCardNumber)).getText()) &&
                    !TextUtils.isEmpty(((EditText) findViewById(R.id.edittextCardNumberMoney)).getText()) &&
                    Integer.parseInt(((EditText) findViewById(R.id.edittextCardNumberMoney)).getText().toString()) >= 1000) {
                if ( Money > Integer.parseInt(((EditText) findViewById(R.id.edittextCardNumberMoney)).getText().toString())) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MoneyOutActivity.this)
                            .setMessage("Hisobingizdan chiqarmoqchi bo'lgan pul miqdori 1 sutka ichida plastik kartangizga o'tkazib beriladi. " +
                                    "Iltimos pul plastik kartangizga tushmaguniga qadar boshqa pul chiqarish amaliyotini bajarmang!")
                            .setPositiveButton("O'tkazish", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    FileOutputStream fileout = null;
                                    try {
                                        fileout = openFileOutput("money_out_card.krm", MODE_PRIVATE);
                                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileout));
                                        bufferedWriter.write("money: " + ((EditText) findViewById(R.id.edittextCardNumberMoney)).getText().toString());
                                        bufferedWriter.append("\r\ncard number: " + ((EditText) findViewById(R.id.edittextCardNumber)).getText().toString());

                                        bufferedWriter.close();
                                        fileout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    uploadFile("/data/user/0/com.ARCompany.Karmon/files/money_out_card.krm");
                                    progressDialog.setMessage("Ma'lumotlar yuborilmoqda...");
                                    progressDialog.show();
                                }
                            })
                            .setNegativeButton("Bekor qilish", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create().show();
                } else {
                    Toast.makeText(MoneyOutActivity.this, "Hisobdagi pul yetarli emas!", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(MoneyOutActivity.this, "Iltimos, ma'lumotlarni to'liq va to'g'ri kiriting!", Toast.LENGTH_LONG).show();
            }
        }
        if (view == btn_money_out_account) {
            account_clicked=true;
            card_clicked=false;
            phone_clicked=false;
            if (!TextUtils.isEmpty(((EditText) findViewById(R.id.edittextAccount)).getText()) &&
                    !TextUtils.isEmpty(((EditText) findViewById(R.id.edittextAccountMoney)).getText()) &&
                    ((EditText) findViewById(R.id.edittextAccount)).getText().toString().contains("@")&&
                    Integer.parseInt(((EditText) findViewById(R.id.edittextAccountMoney)).getText().toString()) >= 1000) {

                if (Money > Integer.parseInt(((EditText) findViewById(R.id.edittextAccountMoney)).getText().toString())) {

                progressDialog.setMessage("Tekshirilmoqda...");
                progressDialog.show();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Boolean RegisteredPhone=false;
                                //Get map of users in datasnapshot
                                for (Map.Entry<String, Object> entry : ((Map<String, Object> )dataSnapshot.getValue()).entrySet()) {
                                    //Get user map
                                    Map singleUser = (Map) entry.getValue();
                                    String email = ((EditText) findViewById(R.id.edittextAccount)).getText().toString();

                                    if(singleUser.get("Phone").toString().toLowerCase().equals(email.toLowerCase())){
                                        RegisteredPhone=true;
                                        break;
                                    }
                                }

                                if(RegisteredPhone){
                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MoneyOutActivity.this)
                                            .setMessage("Hisobingizdan chiqarmoqchi bo'lgan pul miqdori 1 sutka ichida ko'rsatilgan hisobga o'tkazib beriladi. " +
                                                    "Iltimos pul ko'rsatilgan hisobga tushmaguniga qadar boshqa pul chiqarish amaliyotini bajarmang!")
                                            .setPositiveButton("O'tkazish", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    FileOutputStream fileout = null;
                                                    try {
                                                        fileout = openFileOutput("money_out_account.krm", MODE_PRIVATE);
                                                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileout));
                                                        bufferedWriter.write("money: " + ((EditText) findViewById(R.id.edittextAccountMoney)).getText().toString());
                                                        bufferedWriter.append("\r\naccount: " + ((EditText) findViewById(R.id.edittextAccount)).getText().toString());

                                                        bufferedWriter.close();
                                                        fileout.close();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }


                                                    uploadFile("/data/user/0/com.ARCompany.Karmon/files/money_out_account.krm");
                                                    progressDialog.setMessage("Ma'lumotlar yuborilmoqda...");
                                                    progressDialog.show();
                                                }
                                            })
                                            .setNegativeButton("Bekor qilish", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            });
                                    builder.create().show();
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(MoneyOutActivity.this,"Kiritilgan telefon raqami ro'yxatdan o'tkazilmagan",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });

                } else {
                    Toast.makeText(MoneyOutActivity.this, "Hisobdagi pul yetarli emas!", Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(MoneyOutActivity.this, "Iltimos, ma'lumotlarni to'liq va to'g'ri kiriting!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadFile(final String path) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = storageRef.child( firebaseUser.getUid()+"/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                progressDialog.dismiss();
                Toast.makeText(MoneyOutActivity.this,"Failed",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                progressDialog.dismiss();
                Toast.makeText(MoneyOutActivity.this, "Success", Toast.LENGTH_LONG).show();
                databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());
                Map<String, Object> mHashmap = new HashMap<>();
                Integer out_money=0;
                if(phone_clicked) {
                    out_money = Integer.parseInt(((EditText) findViewById(R.id.edittextPhoneMoney)).getText().toString());
                }
                if(card_clicked){
                    out_money = Integer.parseInt(((EditText) findViewById(R.id.edittextCardNumberMoney)).getText().toString());
                }
                if(account_clicked){
                    out_money = Integer.parseInt(((EditText) findViewById(R.id.edittextAccountMoney)).getText().toString());
                }
                mHashmap.put("Money", String.valueOf(Money - out_money));
                databaseReference.updateChildren(mHashmap);
                finish();

            }
        });
    }
}
