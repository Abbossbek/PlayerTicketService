package com.ARCompany.KarmonAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

public class AddAd extends AppCompatActivity implements View.OnClickListener {
    private TextView regions_choose, profs_choose;
    private Boolean[] selected_regions, selected_profs;
    private NumberPicker numberPickerMin, numberPickerMax;
    private CardView count_users, card_send, card_send_me, card_send_to_self;
    private CheckBox checkMan, checkWoman;
    private int number_of_users=0;
    private EditText ad_uid, ad_title, ad_body, ad_pay, ad_url, ad_url_site;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        regions_choose = (TextView) findViewById(R.id.regions_choose);
        regions_choose.setOnClickListener(this);
        profs_choose = (TextView) findViewById(R.id.profs_choose);
        profs_choose.setOnClickListener(this);
        selected_regions = new Boolean[getResources().getStringArray(R.array.regions).length];
        selected_profs = new Boolean[getResources().getStringArray(R.array.professions).length];
        for (int i = 0; i < selected_regions.length; i++) {
            selected_regions[i] = true;
        }
        for (int i = 0; i < selected_profs.length; i++) {
            selected_profs[i] = true;
        }

        numberPickerMin=(NumberPicker)findViewById(R.id.number_pickerMin);
        numberPickerMin.setMaxValue(100);
        numberPickerMin.setMinValue(0);
        numberPickerMin.setValue(7);
        numberPickerMax=(NumberPicker)findViewById(R.id.number_pickerMax);
        numberPickerMax.setMaxValue(100);
        numberPickerMax.setMinValue(0);
        numberPickerMax.setValue(77);

        checkMan=(CheckBox)findViewById(R.id.CheckMan);
        checkWoman=(CheckBox)findViewById(R.id.CheckWoman);

        count_users=(CardView)findViewById(R.id.count_users);
        count_users.setOnClickListener(this);
        card_send_me=(CardView)findViewById(R.id.card_send_me);
        card_send_me.setOnClickListener(this);
        card_send=(CardView)findViewById(R.id.card_send);
        card_send.setOnClickListener(this);
        card_send_to_self=(CardView)findViewById(R.id.card_send_to_self);
        card_send_to_self.setOnClickListener(this);

        ad_uid=(EditText)findViewById(R.id.ad_uid);
        ad_title=(EditText)findViewById(R.id.ad_title);
        ad_body=(EditText)findViewById(R.id.ad_body);
        ad_url=(EditText)findViewById(R.id.ad_url);
        ad_url_site=(EditText)findViewById(R.id.ad_url_site);
        ad_pay=(EditText)findViewById(R.id.ad_pay);

        progressDialog=new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {
        if(view == card_send_me){
            DatabaseReference databaseReference1;

            Map<String, Object> mHashmap = new HashMap<>();


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
            FirebaseDatabase.getInstance().getReference("wcwSMES0TjYpAJky3IITyGOSJxI2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean have_ad=false;
                    for (DataSnapshot ad : dataSnapshot.child("Ads").getChildren()) {
                        String title = ad.child("title").getValue().toString();
                        if (ad_title.getText().toString().equals(title)) {
                            have_ad=true;
                            break;
                        }
                    }

                    if(!have_ad){
                        mHashmap.put(date_time+"/title", ad_title.getText().toString());
                        mHashmap.put(date_time+"/body", ad_body.getText().toString());
                        mHashmap.put(date_time+"/url", ad_url.getText().toString());
                        mHashmap.put(date_time+"/clicked", "0");
                        mHashmap.put(date_time+"/pay", ad_pay.getText().toString());
                        mHashmap.put(date_time+"/ad_url_site", ad_url_site.getText().toString());


                        FirebaseDatabase.getInstance().getReference("wcwSMES0TjYpAJky3IITyGOSJxI2"+"/"+"Ads").
                        updateChildren(mHashmap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                Toast.makeText(AddAd.this, "Yuborildi!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        if(view == card_send_to_self){
            try {
                DatabaseReference databaseReference1;

                Map<String, Object> mHashmap = new HashMap<>();


                int year = Calendar.getInstance().get(Calendar.YEAR);
                String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
                String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                String minut = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
                String secund = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
                if (day.length() != 2) {
                    day = "0" + day;
                }
                if (month.length() != 2) {
                    month = "0" + month;
                }
                if (hour.length() != 2) {
                    hour = "0" + hour;
                }
                if (minut.length() != 2) {
                    minut = "0" + minut;
                }
                if (secund.length() != 2) {
                    secund = "0" + secund;
                }

                String date_time = year + "_" + month + "_" + day + "_" + hour + "_" + minut + "_" + secund;

                mHashmap.put(date_time + "/title", ad_title.getText().toString());
                mHashmap.put(date_time + "/body", ad_body.getText().toString());
                mHashmap.put(date_time + "/url", ad_url.getText().toString());
                mHashmap.put(date_time + "/clicked", "0");
                mHashmap.put(date_time + "/pay", ad_pay.getText().toString());
                mHashmap.put(date_time + "/ad_url_site", ad_url_site.getText().toString());


                databaseReference1 = FirebaseDatabase.getInstance().getReference(ad_uid.getText().toString() + "/" + "My_ads");
                databaseReference1.updateChildren(mHashmap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Toast.makeText(AddAd.this, "Yuborildi!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch(Exception ex){
                Toast.makeText(AddAd.this, "Xatolik yuz berdi!", Toast.LENGTH_LONG).show();
            }
        }
        if (view == regions_choose) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Viloyatlarni belgilang!")
                    .setMultiChoiceItems(R.array.regions, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            selected_regions[i] = b;
                        }
                    })
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    AlertDialog dialog = (AlertDialog) dialogInterface;
                    ListView v = dialog.getListView();
                    int j = 0;
                    while (j < selected_regions.length) {
                        v.setItemChecked(j, selected_regions[j]);
                        j++;
                    }
                }
            });
            alertDialog.show();
        }
        if (view == profs_choose) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Kasblarni belgilang!")
                    .setMultiChoiceItems(R.array.professions, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            selected_profs[i] = b;
                        }
                    })
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    AlertDialog dialog = (AlertDialog) dialogInterface;
                    ListView v = dialog.getListView();
                    int j = 0;
                    while (j < selected_profs.length) {
                        v.setItemChecked(j, selected_profs[j]);
                        j++;
                    }
                }
            });
            alertDialog.show();
        }
        if(view==count_users){
            progressDialog.setMessage("Hisoblanmoqda...");
            progressDialog.show();
            //Get datasnapshot at your "users" root node
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot
                            CalculateUsers((Map<String,Object>) dataSnapshot.getValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        if(view==card_send){
            if(!TextUtils.isEmpty(ad_title.getText())&&!TextUtils.isEmpty(ad_body.getText())&&
                    !TextUtils.isEmpty(ad_url.getText())&&!TextUtils.isEmpty(ad_pay.getText())){
                progressDialog.setMessage("Yuborilmoqda...");
                progressDialog.show();
                //Get datasnapshot at your "users" root node
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
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
                                //Get map of users in datasnapshot
                                SendAd(dataSnapshot, date_time);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });
            }
            else{
                Toast.makeText(this, "Ma'lumotlarni to'liq kiriting.", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void CalculateUsers(Map<String, Object> value) {

        number_of_users=0;

        Boolean selected_user=true;

        for (Map.Entry<String, Object> entry : value.entrySet()){
            if(entry.getKey().toString().equals("Users")){
                continue;
            }
            //Get user map
            Map singleUser = (Map) entry.getValue();
            selected_user=true;

            String date=singleUser.get("Date").toString();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int dateYear=Integer.parseInt(date.substring(date.length()-4,date.length()));
            int age = year - dateYear;
            if(age>numberPickerMax.getValue()||age<numberPickerMin.getValue()){
                selected_user=false;
                continue;
            }

            String profession = singleUser.get("Profession").toString();
            for(int i=0;i<selected_profs.length;i++){
                String profession1=getResources().getStringArray(R.array.professions)[i];
                if(!selected_profs[i]&&profession1.equals(profession)){
                    selected_user=false;
                    continue;
                }
            }

            String region = singleUser.get("Region").toString();
            for(int i=0;i<selected_regions.length;i++){
                if(!selected_regions[i]&&getResources().getStringArray(R.array.regions)[i].equals(region)){
                    selected_user=false;
                    continue;
                }
            }

            String sex=singleUser.get("Sex").toString();
            if(sex.equals("1")&&!checkMan.isChecked()||sex.equals("0")&&!checkWoman.isChecked()){
                selected_user=false;
                continue;
            }

            if(selected_user){
                number_of_users++;
            }
        }
        ((TextView)findViewById(R.id.users_count)).setText(String.valueOf(number_of_users));

        ((TableRow)findViewById(R.id.table_row_cost)).setVisibility(View.VISIBLE);
        ((NumberPicker)findViewById(R.id.number_pickerUsers)).setMaxValue(number_of_users);
        ((NumberPicker)findViewById(R.id.number_pickerUsers)).setMinValue(0);
        ((NumberPicker)findViewById(R.id.number_pickerUsers)).setValue(number_of_users);
        ((TableRow)findViewById(R.id.table_row_pay)).setVisibility(View.VISIBLE);


        progressDialog.dismiss();
    }

    private void SendAd(DataSnapshot dataSnapshot, String date_time) {

        int users_add_send=0;

        Boolean selected_user=true;

        for (DataSnapshot user : dataSnapshot.getChildren()){
            if(user.getKey().equals("Users")){
                    continue;
                }


            selected_user=true;

            String date=user.child("Date").getValue().toString();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int dateYear=Integer.parseInt(date.substring(date.length()-4,date.length()));
            int age = year - dateYear;
            if(age>numberPickerMax.getValue()||age<numberPickerMin.getValue()){
                selected_user=false;
                continue;
            }

            String profession = user.child("Profession").getValue().toString();
            for(int i=0;i<selected_profs.length;i++){
                String profession1=getResources().getStringArray(R.array.professions)[i];
                if(!selected_profs[i]&&profession1.equals(profession)){
                    selected_user=false;
                    continue;
                }
            }

            String region = user.child("Region").getValue().toString();
            for(int i=0;i<selected_regions.length;i++){
                if(!selected_regions[i]&&getResources().getStringArray(R.array.regions)[i].equals(region)){
                    selected_user=false;
                    continue;
                }
            }

            String sex=user.child("Sex").getValue().toString();
            if(sex.equals("1")&&!checkMan.isChecked()||sex.equals("0")&&!checkWoman.isChecked()){
                selected_user=false;
                continue;
            }

            if(selected_user){

                boolean have_ad=false;
                for (DataSnapshot ad : user.child("Ads").getChildren()) {
                    String title = ad.child("title").getValue().toString();
                    if (ad_title.getText().toString().equals(title)) {
                        have_ad=true;
                        break;
                    }
                }
                if(!have_ad) {
                    DatabaseReference databaseReference1;

                    Map<String, Object> mHashmap = new HashMap<>();

                    mHashmap.put(date_time + "/title", ad_title.getText().toString());
                    mHashmap.put(date_time + "/body", ad_body.getText().toString());
                    mHashmap.put(date_time + "/url", ad_url.getText().toString());
                    mHashmap.put(date_time + "/clicked", "0");
                    mHashmap.put(date_time + "/pay", ad_pay.getText().toString());
                    mHashmap.put(date_time + "/ad_url_site", ad_url_site.getText().toString());


                    databaseReference1 = FirebaseDatabase.getInstance().getReference(user.getKey() + "/" + "Ads");
                    databaseReference1.updateChildren(mHashmap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        }
                    });

                    users_add_send++;
                    if(users_add_send==((NumberPicker)findViewById(R.id.number_pickerUsers)).getValue()){
                        break;
                    }
                }
            }
        }

        Toast.makeText(AddAd.this, "Reklama foydalanuvchilarga yuborildi.", Toast.LENGTH_LONG).show();

        progressDialog.dismiss();
    }

}
