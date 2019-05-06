package com.example.smartpill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

public class popupActivity extends AppCompatActivity {

    public static final String MEDICINE = "medicine";
    public static final String QUANTITY = "quantity";
    public static final String DURATION = "duration";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";
    public static final String MON = "monday";
    public static final String TUES = "tuesday";
    public static final String WEDNES = "wednesday";
    public static final String THURS = "thursday";
    public static final String FRI = "friday";
    public static final String SATUR = "saturday";
    public static final String SUN = "sunday";

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_ACTIVITY_REQUEST_CODE = 2;

    //private Button btn_delete;
    //private Button btn_edit;
    int position;
    int SID;
    String medicine;
    String quantity;
    Integer duration;
    Integer hour;
    Integer minute;
    Integer mon;
    Integer tues;
    Integer wed;
    Integer thurs;
    Integer fri;
    Integer sat;
    Integer sun;

    public WordViewModel mWordViewModel;
    public List<Word> mListWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.9),(int)(height*.62));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 0;

        getWindow().setAttributes(params);
        getIncomingIntent();

        mWordViewModel = ViewModelProviders.of(homeActivity.instance).get(WordViewModel.class);

        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra("update", 0);
                replyIntent.putExtra("position", position);
                replyIntent.putExtra(MEDICINE, medicine);
                replyIntent.putExtra(QUANTITY,quantity);
                replyIntent.putExtra(DURATION,duration);
                replyIntent.putExtra(HOUR,hour);
                replyIntent.putExtra(MINUTE,minute);
                replyIntent.putExtra(MON,mon);
                replyIntent.putExtra(TUES,tues);
                replyIntent.putExtra(WEDNES,wed);
                replyIntent.putExtra(THURS,thurs);
                replyIntent.putExtra(FRI,fri);
                replyIntent.putExtra(SATUR,sat);
                replyIntent.putExtra(SUN,sun);
                setResult(RESULT_OK, replyIntent);

                finish();
            }
        });

        Button btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra("update", 1);
                replyIntent.putExtra("position", position);
                replyIntent.putExtra(MEDICINE, medicine);
                replyIntent.putExtra(QUANTITY,quantity);
                replyIntent.putExtra(DURATION,duration);
                replyIntent.putExtra(HOUR,hour);
                replyIntent.putExtra(MINUTE,minute);
                replyIntent.putExtra(MON,mon);
                replyIntent.putExtra(TUES,tues);
                replyIntent.putExtra(WEDNES,wed);
                replyIntent.putExtra(THURS,thurs);
                replyIntent.putExtra(FRI,fri);
                replyIntent.putExtra(SATUR,sat);
                replyIntent.putExtra(SUN,sun);
                setResult(RESULT_OK, replyIntent);

                finish();
            }
        });

    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("SID")) {
            position = getIntent().getIntExtra("position", 0);
            SID = getIntent().getIntExtra("SID", 0);
            medicine = getIntent().getStringExtra("medicine");
            quantity = getIntent().getStringExtra("quantity");
            duration = getIntent().getIntExtra("duration", 0);
            hour = getIntent().getIntExtra("hour", 0);
            minute = getIntent().getIntExtra("minute", 0);
            mon = getIntent().getIntExtra("mon", 0);
            tues = getIntent().getIntExtra("tues", 0);
            wed = getIntent().getIntExtra("wed", 0);
            thurs = getIntent().getIntExtra("thurs", 0);
            fri = getIntent().getIntExtra("fri", 0);
            sat = getIntent().getIntExtra("sat", 0);
            sun = getIntent().getIntExtra("sun", 0);

            setPopup(SID, medicine, quantity, hour, minute, mon, tues, wed, thurs, fri, sat, sun);
        }
    }

    private void setPopup(int SID, String medicine, String quantity, Integer hour, Integer minute, Integer mon, Integer tues, Integer wed, Integer thurs, Integer fri, Integer sat, Integer sun) {
        TextView med = findViewById(R.id.popup_medicine);
        med.setText(medicine);

        TextView quan = findViewById(R.id.popup_quantity);
        quan.setText(quantity);

        String timer = String.format("%02d:%02d %s", hour == 0 || hour == 12 ? 12 : hour%12,
                minute,hour > 11 ? "PM" : "AM");
        TextView time = findViewById(R.id.popup_time);
        time.setText(timer);

        CustomToggleButton monday = (CustomToggleButton) findViewById(R.id.popup_mon);
        if(mon == 1){
            monday.setChecked(true);
        }
        else {
            monday.setChecked(false);
        }

        CustomToggleButton tuesday = (CustomToggleButton) findViewById(R.id.popup_tue);
        if(tues == 1){
            tuesday.setChecked(true);
        }
        else {
            tuesday.setChecked(false);
        }

        CustomToggleButton wednesday = (CustomToggleButton) findViewById(R.id.popup_wed);
        if(wed == 1){
            wednesday.setChecked(true);
        }
        else {
            wednesday.setChecked(false);
        }

        CustomToggleButton thursday = (CustomToggleButton) findViewById(R.id.popup_thurs);
        if(thurs == 1){
            thursday.setChecked(true);
        }
        else {
            thursday.setChecked(false);
        }

        CustomToggleButton friday = (CustomToggleButton) findViewById(R.id.popup_fri);
        if(fri == 1){
            friday.setChecked(true);
        }
        else {
            friday.setChecked(false);
        }

        CustomToggleButton saturday = (CustomToggleButton) findViewById(R.id.popup_sat);
        if(sat == 1){
            saturday.setChecked(true);
        }
        else {
            saturday.setChecked(false);
        }

        CustomToggleButton sunday = (CustomToggleButton) findViewById(R.id.popup_sun);
        if(sun == 1){
            sunday.setChecked(true);
        }
        else {
            sunday.setChecked(false);
        }

    }

}
