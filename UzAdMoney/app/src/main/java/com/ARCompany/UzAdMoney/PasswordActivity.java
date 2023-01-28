package com.ARCompany.UzAdMoney;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PasswordActivity extends AppCompatActivity {

    private EditText editTextPassword, editTextPassword2;
    private Button buttonSigninPassword;
    private TextView text_resend_code;
    private ProgressDialog progressDialog;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private String password;
    private boolean password_loaded=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());

        if(Values.passed){
            finish();
            startActivity(new Intent(PasswordActivity.this, ProfileActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        switch (Values.theme){
            case 0:
                ((RelativeLayout)findViewById(R.id.relative_registration_mail)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((Button)findViewById(R.id.buttonSigninPassword)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                break;
            case 1:
                ((RelativeLayout)findViewById(R.id.relative_registration_mail)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((Button)findViewById(R.id.buttonSigninPassword)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                break;
            case 2:
                ((RelativeLayout)findViewById(R.id.relative_registration_mail)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((Button)findViewById(R.id.buttonSigninPassword)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                break;
        }

        editTextPassword=(TextInputEditText)findViewById(R.id.edittextPassword);
        editTextPassword2=(TextInputEditText)findViewById(R.id.edittextPassword2);
        buttonSigninPassword=(Button)findViewById(R.id.buttonSigninPassword);
        text_resend_code=(TextView) findViewById(R.id.text_resend_code);
        progressDialog =new ProgressDialog(this);

        if(Values.signed_in) {
            FirebaseDatabase.getInstance().getReference().child("Users")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                if(!Values.password_changed) {
                                    password = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue().toString();
                                }
                                else {
                                    Values.signed_in=false;
                                    Values.password_changed=false;
                                    ((TextView)findViewById(R.id.text_sign_in_password)).setText("PAROL YARATING");
                                    ((TextInputLayout)findViewById(R.id.text_layout_password2)).setVisibility(View.VISIBLE);
                                    text_resend_code.setVisibility(View.GONE);
                                }
                            } catch (Exception ex) {
                                Values.signed_in=false;
                                ((TextView)findViewById(R.id.text_sign_in_password)).setText("PAROL YARATING");
                                ((TextInputLayout)findViewById(R.id.text_layout_password2)).setVisibility(View.VISIBLE);
                                text_resend_code.setVisibility(View.GONE);
                            }
                            password_loaded=true;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }

        buttonSigninPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextPassword.getText())){
                    Toast.makeText(PasswordActivity.this,"Iltimos, parolni kiriting!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(Values.signed_in){
                    if(!password_loaded){
                        Toast.makeText(PasswordActivity.this,"Internet aloqasini tekshiring!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(editTextPassword.getText().toString().equals(password)) {
                        Values.passed=true;
                        finish();
                        startActivity(new Intent(PasswordActivity.this, ProfileActivity.class));
                    }
                    else {
                        Toast.makeText(PasswordActivity.this,"Parol noto'g'ri kiritildi!",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    if(!editTextPassword.getText().toString().equals(editTextPassword2.getText().toString())) {
                        Toast.makeText(PasswordActivity.this,"Parol tasdiqlanmadi!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    Map<String, Object> mHashmap = new HashMap<>();
                    mHashmap.put(FirebaseAuth.getInstance().getCurrentUser().getUid(), editTextPassword.getText().toString());
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .updateChildren(mHashmap);
                    Values.passed=true;
                    Values.signed_in=true;
                    try{
                        FirebaseDatabase.getInstance().getReference()
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        finish();
                        startActivity(new Intent(PasswordActivity.this, ProfileActivity.class));
                    }
                    catch (Exception ex) {
                        finish();
                        startActivity(new Intent(PasswordActivity.this, RegisterActivity.class));
                    }
                }
            }
        });

        if(!Values.signed_in){
            ((TextView)findViewById(R.id.text_sign_in_password)).setText("PAROL YARATING");
            ((TextInputLayout)findViewById(R.id.text_layout_password2)).setVisibility(View.VISIBLE);
            text_resend_code.setVisibility(View.GONE);
        }

        text_resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PasswordActivity.this)
                        .setMessage("Hisobingizga kirish parolini esdan chiqargan bo'lsangiz biz telefon raqamingiz sizda ekanligini tekshiramiz! " +
                                "Agar tekshiruv muvaffaqiyatli amalga oshmasa sms xabar orqali sizga kod yuboriladi va yangi parol yaratishingizga imkon beriladi!")
                        .setPositiveButton("Tasdiqlash", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Values.password_changed=true;
                                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                if (!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {

                                    Toast.makeText(PasswordActivity.this, "Internet aloqasini tekshiring va qaytadan urinib ko'ring!", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                sendCode(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                                progressDialog.setMessage("Tekshirilmoqda...");
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
        });
    }

    private void sendCode(String phoneNumber) {
        setUpVerificationCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);
    }

    private void setUpVerificationCallbacks() {
        verificationCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressDialog.dismiss();

                Values.passed=false;
                Values.signed_in=false;
                finish();
                startActivity(new Intent(PasswordActivity.this, PasswordActivity.class));
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.dismiss();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(PasswordActivity.this, "Xatolik yuz berdi!", Toast.LENGTH_LONG).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(PasswordActivity.this, "Juda ko'p urinish amalga oshirildi. Iltimos keyinroq qayta urinib ko'ring.", Toast.LENGTH_LONG).show();
                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(PasswordActivity.this)
                            .setMessage(e.getMessage())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create().show();
                    // Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                progressDialog.dismiss();
                Values.phoneVerificationId=s;
                Toast.makeText(PasswordActivity.this,"Kod telefon raqamingizga yuborildi!",Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(PasswordActivity.this, RegistrationEmail.class));
            }
        };
    }


}
