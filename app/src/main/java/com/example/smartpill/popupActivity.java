package com.example.smartpill;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

public class popupActivity extends AppCompatActivity {
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
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("SID")) {
            int SID = getIntent().getIntExtra("SID", 0);
            String medicine = getIntent().getStringExtra("medicine");
            String quantity = getIntent().getStringExtra("quantity");
            Integer hour = getIntent().getIntExtra("hour", 0);
            Integer minute = getIntent().getIntExtra("minute", 0);
            Integer mon = getIntent().getIntExtra("mon", 0);
            Integer tues = getIntent().getIntExtra("tues", 0);
            Integer wed = getIntent().getIntExtra("wed", 0);
            Integer thurs = getIntent().getIntExtra("thurs", 0);
            Integer fri = getIntent().getIntExtra("fri", 0);
            Integer sat = getIntent().getIntExtra("sat", 0);
            Integer sun = getIntent().getIntExtra("sun", 0);


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
