package com.arcompany.toshqaychiqogoz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Timer _timer = new Timer();

    private Toolbar _toolbar;
    private FloatingActionButton _fab;
    private int i = 0;
    private boolean started = false;
    private int j = 0;
    private double selected = 0;
    private boolean IsSelected = false;
    private double ochko1 = 0;
    private double ochko2 = 0;

    private LinearLayout linear1;
    private ScrollView vscroll1;
    private ScrollView vscroll2;
    private LinearLayout linear13;
    private LinearLayout linear8;
    private LinearLayout linear4;
    private LinearLayout linear2;
    private LinearLayout linear9;
    private LinearLayout linear10;
    private LinearLayout linear11;
    private ImageView imageview8;
    private ImageView imageview9;
    private ImageView imageview10;
    private TextView textview1;
    private LinearLayout linear12;
    private Button button2;
    private TextView textview4;
    private TextView textview2;
    private TextView textview5;
    private LinearLayout linear5;
    private LinearLayout linear6;
    private LinearLayout linear7;
    private ImageView imageview2;
    private ImageView imageview3;
    private ImageView imageview4;

    private TimerTask timer;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initialize(savedInstanceState);
        initializeLogic();

        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://t.me/CSharp_N1"));
                startActivity(intent);
            }
        });
    }

    private void initialize(Bundle _savedInstanceState) {

        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _v) {
                onBackPressed();
            }
        });
        _fab = (FloatingActionButton) findViewById(R.id.fab);

        linear1 = (LinearLayout) findViewById(R.id.linear1);
        vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
        vscroll2 = (ScrollView) findViewById(R.id.vscroll2);
        linear13 = (LinearLayout) findViewById(R.id.linear13);
        linear8 = (LinearLayout) findViewById(R.id.linear8);
        linear4 = (LinearLayout) findViewById(R.id.linear4);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear9 = (LinearLayout) findViewById(R.id.linear9);
        linear10 = (LinearLayout) findViewById(R.id.linear10);
        linear11 = (LinearLayout) findViewById(R.id.linear11);
        imageview8 = (ImageView) findViewById(R.id.imageview8);
        imageview9 = (ImageView) findViewById(R.id.imageview9);
        imageview10 = (ImageView) findViewById(R.id.imageview10);
        textview1 = (TextView) findViewById(R.id.textview1);
        linear12 = (LinearLayout) findViewById(R.id.linear12);
        button2 = (Button) findViewById(R.id.button2);
        textview4 = (TextView) findViewById(R.id.textview4);
        textview2 = (TextView) findViewById(R.id.textview2);
        textview5 = (TextView) findViewById(R.id.textview5);
        linear5 = (LinearLayout) findViewById(R.id.linear5);
        linear6 = (LinearLayout) findViewById(R.id.linear6);
        linear7 = (LinearLayout) findViewById(R.id.linear7);
        imageview2 = (ImageView) findViewById(R.id.imageview2);
        imageview3 = (ImageView) findViewById(R.id.imageview3);
        imageview4 = (ImageView) findViewById(R.id.imageview4);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                started = true;
                button2.setText("TANLANG! ");
                i = 5;
                imageview2.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                linear5.setBackgroundColor(Color.TRANSPARENT);
                imageview3.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                linear6.setBackgroundColor(Color.TRANSPARENT);
                imageview4.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                linear7.setBackgroundColor(Color.TRANSPARENT);
                imageview8.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                linear9.setBackgroundColor(Color.TRANSPARENT);
                imageview9.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                linear10.setBackgroundColor(Color.TRANSPARENT);
                imageview10.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                linear11.setBackgroundColor(Color.TRANSPARENT);
                timer = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                button2.setEnabled(false);
                                textview2.setText("TANLANG! ");
                                textview2.setTextColor(0xFF4CAF50);
                                textview1.setText("Vaqt: ".concat(String.valueOf((long)(i))).concat(" sekund"));
                                if (i == 0) {
                                    textview1.setText("NATIJA");
                                    button2.setText("BOSHLASH");
                                    button2.setEnabled(true);
                                    timer.cancel();
                                    if (IsSelected) {
                                        IsSelected = false;
                                        j = getRandom(1,3);
                                        if (j == 1) {
                                            imageview8.setColorFilter(0xFFFFEB3B, PorterDuff.Mode.MULTIPLY);
                                            linear9.setBackgroundColor(0xFF4CAF50);
                                            imageview9.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                                            linear10.setBackgroundColor(Color.TRANSPARENT);
                                            imageview10.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                                            linear11.setBackgroundColor(Color.TRANSPARENT);
                                            if (selected == 1) {
                                                textview2.setText("DURANG!");
                                                textview2.setTextColor(0xFF2196F3);
                                            }
                                            else {
                                                if (selected == 3) {
                                                    textview2.setText("YUTQAZDINGIZ!");
                                                    textview2.setTextColor(0xFFF44336);
                                                    ochko2++;
                                                    textview4.setText(String.valueOf((long)(ochko2)).concat(" ochko"));
                                                }
                                                else {
                                                    textview2.setText("GALABA!");
                                                    textview2.setTextColor(0xFF4CAF50);
                                                    ochko1++;
                                                    textview5.setText(String.valueOf((long)(ochko1)).concat(" ochko"));
                                                }
                                            }
                                        }
                                        else {
                                            if (j == 2) {
                                                imageview9.setColorFilter(0xFFFFEB3B, PorterDuff.Mode.MULTIPLY);
                                                linear10.setBackgroundColor(0xFF4CAF50);
                                                imageview8.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                                                linear9.setBackgroundColor(Color.TRANSPARENT);
                                                imageview10.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                                                linear11.setBackgroundColor(Color.TRANSPARENT);
                                                if (selected == 2) {
                                                    textview2.setText("DURANG!");
                                                    textview2.setTextColor(0xFF2196F3);
                                                }
                                                else {
                                                    if (selected == 1) {
                                                        textview2.setText("YUTQAZDINGIZ!");
                                                        textview2.setTextColor(0xFFF44336);
                                                        ochko2++;
                                                        textview4.setText(String.valueOf((long)(ochko2)).concat(" ochko"));
                                                    }
                                                    else {
                                                        textview2.setText("GALABA!");
                                                        textview2.setTextColor(0xFF4CAF50);
                                                        ochko1++;
                                                        textview5.setText(String.valueOf((long)(ochko1)).concat(" ochko"));
                                                    }
                                                }
                                            }
                                            else {
                                                imageview10.setColorFilter(0xFFFFEB3B, PorterDuff.Mode.MULTIPLY);
                                                linear11.setBackgroundColor(0xFF4CAF50);
                                                imageview8.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                                                linear9.setBackgroundColor(Color.TRANSPARENT);
                                                imageview9.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                                                linear10.setBackgroundColor(Color.TRANSPARENT);
                                                if (selected == 3) {
                                                    textview2.setText("DURANG!");
                                                    textview2.setTextColor(0xFF2196F3);
                                                }
                                                else {
                                                    if (selected == 2) {
                                                        textview2.setText("YUTQAZDINGIZ!");
                                                        textview2.setTextColor(0xFFF44336);
                                                        ochko2++;
                                                        textview4.setText(String.valueOf((long)(ochko2)).concat(" ochko"));
                                                    }
                                                    else {
                                                        textview2.setText("GALABA!");
                                                        textview2.setTextColor(0xFF4CAF50);
                                                        ochko1++;
                                                        textview5.setText(String.valueOf((long)(ochko1)).concat(" ochko"));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        textview2.setText("TANLAMADINGIZ!");
                                        textview2.setTextColor(0xFFF44336);
                                    }
                                }
                                i--;
                            }
                        });
                    }
                };
                _timer.scheduleAtFixedRate(timer, (int)(0), (int)(1000));
            }
        });

        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (started) {
                    imageview2.setColorFilter(0xFFFFEB3B, PorterDuff.Mode.MULTIPLY);
                    linear5.setBackgroundColor(0xFF4CAF50);
                    imageview3.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                    linear6.setBackgroundColor(Color.TRANSPARENT);
                    imageview4.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                    linear7.setBackgroundColor(Color.TRANSPARENT);
                    IsSelected = true;
                    started = false;
                    selected = 1;
                }
            }
        });

        imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (started) {
                    imageview3.setColorFilter(0xFFFFEB3B, PorterDuff.Mode.MULTIPLY);
                    linear6.setBackgroundColor(0xFF4CAF50);
                    imageview2.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                    linear5.setBackgroundColor(Color.TRANSPARENT);
                    imageview4.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                    linear7.setBackgroundColor(Color.TRANSPARENT);
                    IsSelected = true;
                    started = false;
                    selected = 2;
                }
            }
        });

        imageview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (started) {
                    imageview4.setColorFilter(0xFFFFEB3B, PorterDuff.Mode.MULTIPLY);
                    linear7.setBackgroundColor(0xFF4CAF50);
                    imageview3.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                    linear6.setBackgroundColor(Color.TRANSPARENT);
                    imageview2.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
                    linear5.setBackgroundColor(Color.TRANSPARENT);
                    IsSelected = true;
                    started = false;
                    selected = 3;
                }
            }
        });


    }

    private void initializeLogic() {
        started = false;
        ochko1 = 0;
        ochko2 = 0;
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double)_arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels(){
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels(){
        return getResources().getDisplayMetrics().heightPixels;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AnotherProgramsClick(MenuItem item) {
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=ABBOSBEK"));
        startActivity(intent);
    }
}
