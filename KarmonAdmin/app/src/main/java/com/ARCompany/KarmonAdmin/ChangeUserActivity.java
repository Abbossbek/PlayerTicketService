package com.ARCompany.KarmonAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChangeUserActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Button buttonSave;
    private EditText editTextSurname;
    private EditText editTextName;
    private Spinner spinnerSex;
    private Spinner spinnerRegion;
    private Spinner spinnerProfession;
    private EditText editTextDate;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);

        buttonSave=(Button)findViewById(R.id.buttonSave);
        editTextSurname=(EditText)findViewById(R.id.edittextSurname);
        editTextName=(EditText)findViewById(R.id.edittextName);
        spinnerSex=(Spinner)findViewById(R.id.spinnerSex);
        spinnerRegion=(Spinner)findViewById(R.id.spinnerRegion);
        spinnerProfession=(Spinner)findViewById(R.id.spinnerProf);
        editTextDate=(EditText)findViewById(R.id.edittextDatetime);
        editTextDate.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child(Values.UId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUserInfonmations() {
        String Surname = editTextSurname.getText().toString().trim();
        String Name = editTextName.getText().toString().trim();
        String Sex = spinnerSex.getSelectedItem().toString().equals("Erkak") ? "1" : "0";
        String Region = spinnerRegion.getSelectedItem().toString().trim();
        String Profession = spinnerProfession.getSelectedItem().toString().trim();
        String Date = editTextDate.getText().toString().trim();

        Map<String, Object> map=new HashMap<>();

        map.put("Surname",Surname);
        map.put("Name",Name);
        map.put("Sex",Sex);
        map.put("Region",Region);
        map.put("Profession",Profession);
        map.put("Date",Date);

        FirebaseDatabase.getInstance().getReference()
                .child(Values.UId)
                .updateChildren(map);

        Toast.makeText(this, "Ma'lumotlar saqlandi.", Toast.LENGTH_LONG).show();
    }

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
                updateUserInfonmations();
                finish();

            } else {
                Toast.makeText(ChangeUserActivity.this, "Internet aloqasini tekshiring va qaytadan urinib ko'ring!", Toast.LENGTH_LONG).show();
            }
        }

        if(view == editTextDate){
            Calendar calendar=Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(ChangeUserActivity.this, ChangeUserActivity.this, year, month, day);

            datePickerDialog.show();
        }
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
