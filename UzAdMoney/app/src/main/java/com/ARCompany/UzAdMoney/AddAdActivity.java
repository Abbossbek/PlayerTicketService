package com.ARCompany.UzAdMoney;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Map;

public class AddAdActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView select_image, select_gif, select_video;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private static final int REQUEST_CODE = 1234;
    private CardView btnSendImage, btnSendGif, btnSendVideo;
    private Boolean ImageSelected=false, GifSelected=false,VideoSelected=false;
    private String FilePath;
    private TextView regions_choose, profs_choose;
    private Boolean[] selected_regions, selected_profs;
    private NumberPicker numberPickerMin, numberPickerMax;
    private CardView count_users, card_pay;
    private CheckBox checkMan, checkWoman;
    private int number_of_users=0, cost=0;
    private ProgressDialog progressDialog;



    private int selected_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Values.choosed_theme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        verifyStoragePermissions(this);

        switch (Values.theme){
            case 0:
                ((LinearLayout)findViewById(R.id.line_add_ad1)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((LinearLayout)findViewById(R.id.line_add_ad2)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((LinearLayout)findViewById(R.id.line_add_ad3)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((RelativeLayout)findViewById(R.id.relative_add_ad)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar));
                ((RelativeLayout)findViewById(R.id.relative_add_ad1)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.select_image_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.select_gif_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.select_video_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.btn_send_image_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.btn_send_gif_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.btn_send_video_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.count_users_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                ((TextView)findViewById(R.id.card_pay_t)).setBackground(getResources().getDrawable(R.drawable.profile_back));
                break;

            case 1:
                ((LinearLayout)findViewById(R.id.line_add_ad1)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((LinearLayout)findViewById(R.id.line_add_ad2)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((LinearLayout)findViewById(R.id.line_add_ad3)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((RelativeLayout)findViewById(R.id.relative_add_ad)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_blue));
                ((RelativeLayout)findViewById(R.id.relative_add_ad1)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.select_image_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.select_gif_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.select_video_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.btn_send_image_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.btn_send_gif_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.btn_send_video_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.count_users_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                ((TextView)findViewById(R.id.card_pay_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_blue));
                break;
            case 2:
                ((LinearLayout)findViewById(R.id.line_add_ad1)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((LinearLayout)findViewById(R.id.line_add_ad2)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((LinearLayout)findViewById(R.id.line_add_ad3)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((RelativeLayout)findViewById(R.id.relative_add_ad)).setBackground(getResources().getDrawable(R.drawable.profile_nav_bar_black));
                ((RelativeLayout)findViewById(R.id.relative_add_ad1)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.select_image_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.select_gif_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.select_video_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.btn_send_image_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.btn_send_gif_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.btn_send_video_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.count_users_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                ((TextView)findViewById(R.id.card_pay_t)).setBackground(getResources().getDrawable(R.drawable.profile_back_black));
                break;
        }

        select_image = (CardView) findViewById(R.id.select_image);
        select_image.setOnClickListener(this);
        select_gif = (CardView) findViewById(R.id.select_gif);
        select_gif.setOnClickListener(this);
        select_video = (CardView) findViewById(R.id.select_video);
        select_video.setOnClickListener(this);

        // установите свой путь к файлу на SD-карточке
        String videoSource = "android.resource://" + getPackageName() + "/" + R.raw.ad_video;

        final VideoView videoView = (VideoView) findViewById(R.id.video_ad);

        videoView.setVideoPath(videoSource);

        videoView.setMediaController(new MediaController(this));

        btnSendImage = (CardView) findViewById(R.id.btn_send_image);
        btnSendImage.setOnClickListener(this);
        btnSendGif = (CardView) findViewById(R.id.btn_send_gif);
        btnSendGif.setOnClickListener(this);
        btnSendVideo = (CardView) findViewById(R.id.btn_send_video);
        btnSendVideo.setOnClickListener(this);

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
        numberPickerMin.setValue(0);
        numberPickerMax=(NumberPicker)findViewById(R.id.number_pickerMax);
        numberPickerMax.setMaxValue(100);
        numberPickerMax.setMinValue(0);
        numberPickerMax.setValue(77);

        checkMan=(CheckBox)findViewById(R.id.CheckMan);
        checkWoman=(CheckBox)findViewById(R.id.CheckWoman);

        count_users=(CardView)findViewById(R.id.count_users);
        count_users.setOnClickListener(this);
        card_pay=(CardView)findViewById(R.id.card_pay);
        card_pay.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);
    }

        @SuppressLint("RestrictedApi")
        @Override
        public void onClick (View view){
            if (view == select_image) {
                selected_index = 0;
                Intent in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, RESULT_LOAD_IMAGE);
            }
            if (view == select_gif) {
                selected_index = 1;
                Intent in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, RESULT_LOAD_IMAGE);
            }
            if (view == select_video) {
                selected_index = 2;
                showFileChooser();
            }
            if (view == btnSendImage && ImageSelected) {
                ((ViewFlipper)findViewById(R.id.view_flipper)).showNext();
            }
            if (view == btnSendGif && GifSelected) {
                ((ViewFlipper)findViewById(R.id.view_flipper)).showNext();
            }
            if (view == btnSendVideo && VideoSelected) {
                ((ViewFlipper)findViewById(R.id.view_flipper)).showNext();
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
            if(view == card_pay){
                if(!TextUtils.isEmpty(((EditText)findViewById(R.id.ad_title)).getText())&&
                        !TextUtils.isEmpty(((EditText)findViewById(R.id.ad_body)).getText())&&
                                !TextUtils.isEmpty(((EditText)findViewById(R.id.ad_phone_number)).getText())) {
                    String phone=((EditText)findViewById(R.id.ad_phone_number)).getText().toString();
                    if(!(phone.contains("+9989") && phone.length()==13)) {
                        Toast.makeText(AddAdActivity.this, "Telefon raqami noto'g'ri kiritildi!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setMessage("To'lov qilganingizdan so'ng 1 sutka davomida to'lovingiz tekshiriladi. Agar to'lov muvaffaqiyatli amalga oshirilgan bo'lsa reklamangiz foydalanuvchilarga yuboriladi.")
                            .setPositiveButton("To'lov qilish", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    FileOutputStream fileout = null;
                                    try {
                                        fileout = openFileOutput("new_ad.krm", MODE_PRIVATE);
                                        OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                                        outputWriter.write("title: "+((EditText)findViewById(R.id.ad_title)).getText().toString());
                                        outputWriter.append("\r\nbody: "+((EditText)findViewById(R.id.ad_body)).getText().toString());
                                        outputWriter.append("\r\nurl: "+((EditText)findViewById(R.id.ad_url)).getText().toString());
                                        outputWriter.append("\r\nphone : "+((EditText)findViewById(R.id.ad_url)).getText().toString());
                                        outputWriter.append("\r\nage: " + numberPickerMin.getValue() + "," + numberPickerMax.getValue());
                                        outputWriter.append("\r\nprofessions: ");
                                        for (int j = 0; j < selected_profs.length; j++) {
                                            if (selected_profs[j]) {
                                                outputWriter.append("1");
                                            } else {
                                                outputWriter.append("0");
                                            }
                                        }
                                        outputWriter.append("\r\nregions: ");
                                        for (int j = 0; j < selected_regions.length; j++) {
                                            if (selected_regions[j]) {
                                                outputWriter.append("1");
                                            } else {
                                                outputWriter.append("0");
                                            }
                                        }
                                        outputWriter.append("\r\nsex: man " + checkMan.isChecked() + ", woman " + checkWoman.isChecked());
                                        outputWriter.append("\r\ncount: " + String.valueOf(((NumberPicker)findViewById(R.id.number_pickerUsers)).getValue()));
                                        outputWriter.close();
                                        fileout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    uploadFile("/data/user/0/com.ARCompany.Karmon/files/new_ad.krm");
                                    uploadFile(FilePath);
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
                    Toast.makeText(this,"Iltimos, ma'lumotlani to'liq kiriting!",Toast.LENGTH_LONG).show();
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
            int year =Calendar.getInstance().get(Calendar.YEAR);
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
        ((NumberPicker)findViewById(R.id.number_pickerUsers)).setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                ((TextView)findViewById(R.id.text_cost)).setText(String.valueOf(Integer.valueOf(numberPicker.getValue())*cost));
            }
        });
        ((TableRow)findViewById(R.id.table_row_pay)).setVisibility(View.VISIBLE);

        if(ImageSelected){
            cost=20;
        }
        if(GifSelected){
            cost=30;
        }
        if(VideoSelected){
            cost=40;
        }
        ((TextView)findViewById(R.id.text_cost)).setText(String.valueOf(number_of_users*cost));

        progressDialog.dismiss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            File photo = new File(getFileName(data.getData()));
            double len = photo.length();
            if (len < Math.pow(2, 20)) {
                if (selected_index == 0) {
                    ImageSelected=true;
                    GifSelected=false;
                    VideoSelected=false;

                    ImageView ad_image = (ImageView) findViewById(R.id.ad_image);
                    ad_image.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                    FilePath=picturePath;
                }
                if (selected_index == 1) {
                    ImageSelected=false;
                    GifSelected=true;
                    VideoSelected=false;

                    picturePath = picturePath.toLowerCase();
                    pl.droidsonroids.gif.GifImageView ad_gif = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.ad_gif);
                    ad_gif.setImageURI(Uri.fromFile(new File(picturePath)));

                    FilePath=picturePath;
                }
            } else {
                Toast.makeText(AddAdActivity.this, "Rasm hajmi 1 MB dan oshmasligi kerak", Toast.LENGTH_LONG).show();
            }


        }
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && null != data ) {
            if (selected_index == 2) {
                ImageSelected=false;
                GifSelected=false;
                VideoSelected=true;

                FilePath=getFileName(data.getData());
                File video = new File(FilePath);

                double len = video.length();
                if (len < 2 * Math.pow(2, 20)) {
                    ((VideoView) findViewById(R.id.video_ad)).setVideoURI(data.getData());
                } else {
                    Toast.makeText(AddAdActivity.this, "Video hajmi 2 MB dan oshmasligi kerak", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    private String getFileName(Uri uri) throws IllegalArgumentException {
        // Obtain a cursor with information regarding this uri
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }

        cursor.moveToFirst();

        String fileName = cursor.getString(cursor.getColumnIndex(new String[]{MediaStore.Video.Media.DATA}[0]));

        cursor.close();

        return fileName;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    public void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // Update with mime types
        intent.setType("*/*");
        String[] mimeTypes={"*"};
        // Update with additional mime types here using a String[].
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        // Only pick openable and local files. Theoretically we could pull files from google drive
        // or other applications that have networked files, but that's unnecessary for this example.
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        // REQUEST_CODE = <some-integer>
        startActivityForResult(intent, REQUEST_CODE);
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
                Toast.makeText(AddAdActivity.this,"Failed",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                if(!path.endsWith(".krm")) {
                    progressDialog.dismiss();
                    Intent surf = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:*880*8600130958797666*"
                            + ((TextView) findViewById(R.id.text_cost)).getText().toString() + Uri.encode("#")));
                    startActivity(surf);
                }
                    Toast.makeText(AddAdActivity.this, "Success", Toast.LENGTH_LONG).show();

            }
        });
    }

}