package com.ARCompany.UzAdMoney;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class RegistrationEmail extends AppCompatActivity implements View.OnClickListener {
    private Button buttonVerification;
    private EditText editTextCode;
    private ProgressDialog progressDialog;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendingToken;


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_email);

        switch (Values.theme){
            case 0:
                ((RelativeLayout)findViewById(R.id.relative_registration_mail)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((Button)findViewById(R.id.buttonVerification)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                break;
            case 1:
                ((RelativeLayout)findViewById(R.id.relative_registration_mail)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((Button)findViewById(R.id.buttonVerification)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                break;
            case 2:
                ((RelativeLayout)findViewById(R.id.relative_registration_mail)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((Button)findViewById(R.id.buttonVerification)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                break;
        }

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog =new ProgressDialog(this);

        editTextCode=(EditText)findViewById(R.id.edittextCode);

        buttonVerification=(Button)findViewById(R.id.buttonVerification);

        buttonVerification.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        if(view == buttonVerification){
            if(TextUtils.isEmpty(editTextCode.getText())){
                Toast.makeText(this,"Iltimos, kodni kiriting!",Toast.LENGTH_LONG).show();
                return;
            }

            final String code=editTextCode.getText().toString().trim();

            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(Values.phoneVerificationId,code);
            signIn(credential);
            progressDialog.setMessage("Tekshirilmoqda...");
            progressDialog.show();

        }
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            firebaseUser=task.getResult().getUser();
                            finish();
                            startActivity(new Intent(RegistrationEmail.this, PasswordActivity.class));
                        }
                        else {
                            Toast.makeText(RegistrationEmail.this,"Kod noto'g'ri kiritildi!",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }
                });
    }

}
