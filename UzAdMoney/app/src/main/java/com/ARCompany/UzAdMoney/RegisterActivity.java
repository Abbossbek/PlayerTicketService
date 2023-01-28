package com.ARCompany.UzAdMoney;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Button buttonSave;
    private EditText editTextSurname;
    private EditText editTextName;
    private Spinner spinnerSex;
    private Spinner spinnerRegion;
    private Spinner spinnerProfession;
    private EditText editTextDate;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        switch (Values.theme){
            case 0:
                ((RelativeLayout)findViewById(R.id.relative_registration)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((Button)findViewById(R.id.buttonSave)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                break;
            case 1:
                ((RelativeLayout)findViewById(R.id.relative_registration)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((Button)findViewById(R.id.buttonSave)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                break;
            case 2:
                ((RelativeLayout)findViewById(R.id.relative_registration)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((Button)findViewById(R.id.buttonSave)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                break;
        }



        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        progressDialog =new ProgressDialog(this);

        buttonSave=(Button)findViewById(R.id.buttonSave);
        editTextSurname=(EditText)findViewById(R.id.edittextSurname);
        editTextName=(EditText)findViewById(R.id.edittextName);
        spinnerSex=(Spinner)findViewById(R.id.spinnerSex);
        spinnerRegion=(Spinner)findViewById(R.id.spinnerRegion);
        spinnerProfession=(Spinner)findViewById(R.id.spinnerProf);
        editTextDate=(EditText)findViewById(R.id.edittextDate);
        editTextDate.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        if(Values.informations_change){
            final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(Values.informations_change) {
                        String Name = dataSnapshot.child("Name").getValue().toString();
                        String Surname = dataSnapshot.child("Surname").getValue().toString();
                        String Sex = dataSnapshot.child("Sex").getValue().toString();
                        String Region = dataSnapshot.child("Region").getValue().toString();
                        String Profession = dataSnapshot.child("Profession").getValue().toString();
                        String Date = dataSnapshot.child("Date").getValue().toString();

                        editTextSurname.setText(Surname);
                        editTextName.setText(Name);
                        spinnerSex.setSelection(Sex.equals("0") ? 1 : 0);
                        switch (Region) {
                            case "Toshkent shahri":
                                spinnerRegion.setSelection(0);
                                break;
                            case "Toshkent":
                                spinnerRegion.setSelection(1);
                                break;
                            case "Qashqadaryo":
                                spinnerRegion.setSelection(2);
                                break;
                            case "Surxondaryo":
                                spinnerRegion.setSelection(3);
                                break;
                            case "Buxoro":
                                spinnerRegion.setSelection(4);
                                break;
                            case "Xorazm":
                                spinnerRegion.setSelection(5);
                                break;
                            case "Samarqand":
                                spinnerRegion.setSelection(6);
                                break;
                            case "Navoiy":
                                spinnerRegion.setSelection(7);
                                break;
                            case "Jizzax":
                                spinnerRegion.setSelection(8);
                                break;
                            case "Sirdaryo":
                                spinnerRegion.setSelection(9);
                                break;
                            case "Andijon":
                                spinnerRegion.setSelection(10);
                                break;
                            case "Namangan":
                                spinnerRegion.setSelection(11);
                                break;
                            case "Farg`ona":
                                spinnerRegion.setSelection(12);
                                break;
                            case "Qoraqalpog`iston Respublikasi":
                                spinnerRegion.setSelection(13);
                        }
                        switch (Profession) {
                            case "O`qituvchi":
                                spinnerProfession.setSelection(0);
                                break;
                            case "Dasturchi":
                                spinnerProfession.setSelection(1);
                                break;
                            case "Talaba":
                                spinnerProfession.setSelection(2);
                                break;
                            case "O`quvchi":
                                spinnerProfession.setSelection(3);
                                break;
                            case "Tadbirkor":
                                spinnerProfession.setSelection(4);
                                break;
                            case "Hisobchi":
                                spinnerProfession.setSelection(5);
                                break;
                            case "Dizayner":
                                spinnerProfession.setSelection(6);
                                break;
                            case "Usta":
                                spinnerProfession.setSelection(7);
                                break;
                            case "Sartarosh":
                                spinnerProfession.setSelection(8);
                                break;
                            case "Shifokor":
                                spinnerProfession.setSelection(9);
                                break;
                            case "Ishchi":
                                spinnerProfession.setSelection(10);
                                break;
                            case "Qo`shiqchi":
                                spinnerProfession.setSelection(11);
                                break;
                            case "Aktyor (Aktrisa)":
                                spinnerProfession.setSelection(12);
                                break;
                            case "Dehqon":
                                spinnerProfession.setSelection(13);
                                break;
                            case "IIB xodimi":
                                spinnerProfession.setSelection(14);
                                break;
                            case "Sotuvchi":
                                spinnerProfession.setSelection(15);
                                break;
                            case "Haydovchi":
                                spinnerProfession.setSelection(16);
                                break;
                            case "Quruvchi":
                                spinnerProfession.setSelection(17);
                                break;
                            case "Boshqa":
                                spinnerProfession.setSelection(18);
                                break;
                        }
                        editTextDate.setText(Date);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        if(view == buttonSave) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                if (TextUtils.isEmpty(editTextSurname.getText()) || TextUtils.isEmpty(editTextName.getText()) ||
                        TextUtils.isEmpty(editTextDate.getText())) {
                    Toast.makeText(this, "Iltimos, ma'lumotlani to'liq kiriting!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Values.informations_change) {

                    Values.informations_change = false;
                    updateUserInfonmations();
                    finish();
                } else {
                    saveUserInfonmations();
                    finish();
                    startActivity(new Intent(this, ProfileActivity.class));
                }

            } else {
                Toast.makeText(RegisterActivity.this, "Internet aloqasini tekshiring va qaytadan urinib ko'ring!", Toast.LENGTH_LONG).show();
            }
        }

        if(view == editTextDate){
            Calendar calendar=Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, RegisterActivity.this, year, month, day);

            datePickerDialog.show();
        }
    }


    private void saveUserInfonmations() {
        String Surname = editTextSurname.getText().toString().trim();
        String Name = editTextName.getText().toString().trim();
        String Sex = spinnerSex.getSelectedItem().toString().equals("Erkak") ? "1" : "0";
        String Region = spinnerRegion.getSelectedItem().toString().trim();
        String Profession = spinnerProfession.getSelectedItem().toString().trim();
        String Date = editTextDate.getText().toString().trim();
        String Phone = firebaseAuth.getCurrentUser().getPhoneNumber();

        UserInformations userInformations = new UserInformations(Surname, Name, Sex, Region, Profession, Date, Phone);

        DatabaseReference databaseReference1;
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference.child(firebaseUser.getUid()).setValue(userInformations);
        Map<String, Object> mHashmap = new HashMap<>(),
        mHashmap1=new HashMap<>(), mHashmap2=new HashMap<>(), mHashmap3=new HashMap<>();

        mHashmap.put("4/title", "Assalomu alaykum! Xush kelibsiz!");
        mHashmap.put("4/body", "Hozir sizda Karmon ilovasi orqali yaxshigina foyda olish imkoniyati mavjud. Batafsil bu yerda o'qing.");
        mHashmap.put("4/url", "https://firebasestorage.googleapis.com/v0/b/karmonuz.appspot.com/o/Invite%2Finvite_friends.html?alt=media&token=ad14cb1f-971b-482e-92de-d5593ad93b3d");
        mHashmap.put("4/clicked", "0");
        mHashmap.put("4/pay", "10");

        mHashmap1.put("3/title", "TELEGRAMdagi rasmiy kanalimizga a'zo bo'ling!");
        mHashmap1.put("3/body", "Telegram kanalimizda siz eng so'nggi yangiliklardan xabardor bo'lib borishingiz mumkin. Batafsil bu yerda o'qing.");
        mHashmap1.put("3/url", "https://firebasestorage.googleapis.com/v0/b/karmonuz.appspot.com/o/Invite%2Finvite_channel.html?alt=media&token=dde564e5-8178-4c07-98e4-491785434965");
        mHashmap1.put("3/ad_url_site", "https://t.me/KarmonUz");
        mHashmap1.put("3/clicked", "0");
        mHashmap1.put("3/pay", "10");

        mHashmap2.put("2/title", "INSTAGRAMdagi rasmiy sahifamizga a'zo bo'ling!");
        mHashmap2.put("2/body", "Instagram sahifamizga hisobdan chiqarib olinayotgan pul miqdorlari yuborib boriladi. Batafsil bu yerda o'qing.");
        mHashmap2.put("2/url", "https://firebasestorage.googleapis.com/v0/b/karmonuz.appspot.com/o/Invite%2Finvite_instagram.html?alt=media&token=42e4d884-5b92-4303-adc6-86d4161c1155");
        mHashmap2.put("2/ad_url_site", "https://www.instagram.com/karmonuz?r=nametag");
        mHashmap2.put("2/clicked", "0");
        mHashmap2.put("2/pay", "10");

        mHashmap3.put("1/title", "FACEBOOKdagi rasmiy sahifamizga a'zo bo'ling!");
        mHashmap3.put("1/body", "Facebook sahifamizda Karmon ilovasiga kiritilayotgan o'zgartirish va qo'shimchalardan xabardor bo'lib borishingiz mumkin. Batafsil bu yerda o'qing.");
        mHashmap3.put("1/url", "https://firebasestorage.googleapis.com/v0/b/karmonuz.appspot.com/o/Invite%2Finvite_facebook.html?alt=media&token=6238c8b0-18fd-4a56-90b5-5ff173f29c22");
        mHashmap3.put("1/ad_url_site", "https://m.facebook.com/KarmonUz");
        mHashmap3.put("1/clicked", "0");
        mHashmap3.put("1/pay", "10");

        Map<String, Object> map = new HashMap<>();
        map.put(firebaseUser.getUid(), Surname + " " + Name);
        databaseReference1 = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid() + "/" + "Referals");
        databaseReference1.updateChildren(map);
        databaseReference1 = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid() + "/" + "Ads");
        databaseReference1.updateChildren(mHashmap);
        databaseReference1.updateChildren(mHashmap1);
        databaseReference1.updateChildren(mHashmap2);
        databaseReference1.updateChildren(mHashmap3);


        Toast.makeText(this, "Ma'lumotlar saqlandi.", Toast.LENGTH_LONG).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateUserInfonmations() {
        String Surname=editTextSurname.getText().toString().trim();
        String Name=editTextName.getText().toString().trim();
        String Sex=spinnerSex.getSelectedItem().toString().equals("Erkak")?"1":"0";
        String Region=spinnerRegion.getSelectedItem().toString().trim();
        String Profession = spinnerProfession.getSelectedItem().toString().trim();
        String Date=editTextDate.getText().toString().trim();

        Map<String, Object> map=new HashMap<>();

        map.put("Surname",Surname);
        map.put("Name",Name);
        map.put("Sex",Sex);
        map.put("Region",Region);
        map.put("Profession",Profession);
        map.put("Date",Date);

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference()
                .child(firebaseUser.getUid())
                .updateChildren(map);

        Toast.makeText(this, "Ma'lumotlar saqlandi.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String day, month;
        if(String.valueOf(i2).length()==2){
            day=String.valueOf(i2);
        }
        else{
            day=String.valueOf(i2).replace(String.valueOf(i2),"0"+String.valueOf(i2));
        }
        if(String.valueOf(i1+1).length()==2){
            month=String.valueOf(i1+1);
        }
        else{
            month=String.valueOf(i1+1).replace(String.valueOf(i1+1),"0"+String.valueOf(i1+1));
        }
        editTextDate.setText(day+"."+ month +"."+i);
    }
}
