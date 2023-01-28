package com.ARCompany.UzAdMoney;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignin;
    private EditText editTextPhoneNumber;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            Values.signed_in=true;
            finish();
            startActivity(new Intent(getApplicationContext(), PasswordActivity.class));
        }

        if(Values.theme_chanched){
            Values.theme_chanched=false;
            this.finish();
            this.startActivity(new Intent(this,this.getClass()));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switch (Values.theme){
            case 0:
                ((RelativeLayout)findViewById(R.id.relative_main)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((Button)findViewById(R.id.buttonSignin)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                break;
            case 1:
                ((RelativeLayout)findViewById(R.id.relative_main)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((Button)findViewById(R.id.buttonSignin)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                break;
            case 2:
                ((RelativeLayout)findViewById(R.id.relative_main)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((Button)findViewById(R.id.buttonSignin)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                break;
        }


        progressDialog =new ProgressDialog(this);

        editTextPhoneNumber=(EditText)findViewById(R.id.edittextPhoneNumber);
        buttonSignin=(Button)findViewById(R.id.buttonSignin);


        buttonSignin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == buttonSignin) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                String phone=editTextPhoneNumber.getText().toString().replace(" ","");
                if(phone.contains("+9989") && phone.length()==13) {
                    sendCode(editTextPhoneNumber.getText().toString());
                    progressDialog.setMessage("Tekshirilmoqda...");
                    progressDialog.show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Telefon raqami noto'g'ri kiritildi!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Internet aloqasini tekshiring va qaytadan urinib ko'ring!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void sendCode(String phone){

        setUpVerificationCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
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
                Values.signed_in=true;
                signIn(phoneAuthCredential);
                progressDialog.setMessage("Tizimga kirilmoqda...");
                progressDialog.show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.dismiss();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(MainActivity.this, "Telefon raqami noto'g'ri kiritilgan!", Toast.LENGTH_LONG).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(MainActivity.this, "Juda ko'p urinish amalga oshirildi. Iltimos keyinroq qayta urinib ko'ring.", Toast.LENGTH_LONG).show();
                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
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
                Toast.makeText(MainActivity.this,"Kod telefon raqamingizga yuborildi!",Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(MainActivity.this, RegistrationEmail.class));
            }
        };
    }
    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(MainActivity.this, PasswordActivity.class));
                        }
                    }
                });
    }
}
