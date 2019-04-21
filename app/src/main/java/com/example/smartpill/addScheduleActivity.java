package com.example.smartpill;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

import static android.app.ProgressDialog.show;

public class addScheduleActivity extends AppCompatActivity {
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

    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addschedule);
        /*
        String[] arraySpinner = new String[] {
                "day(s)", "week(s)", "month(s)", "year(s)"
        };
        Spinner s = (Spinner) findViewById(R.id.duration);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        */
        String[] medicineSpinner = new String[] {
                "Paracetamol", "Cefalexin", "Amoxicillin", "Abacavir"
        };
        Spinner smed = (Spinner) findViewById(R.id.medicines_spinner);
        ArrayAdapter<String> medadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, medicineSpinner);
        medadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smed.setAdapter(medadapter);

        Button mBack = (Button) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TimePicker mTimePicker; //time input
        final Spinner mMedicine; //medicine input
        final CustomToggleButton mMon, mTues, mWed, mThurs, mFri, mSat, mSun; //repeat schedule input
        final EditText mQuantity;
        final EditText mDuration;

        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        mMedicine = (Spinner) findViewById(R.id.medicines_spinner);

        mMon = (CustomToggleButton) findViewById(R.id.mon);
        mTues = (CustomToggleButton) findViewById(R.id.tue);
        mWed = (CustomToggleButton) findViewById(R.id.wed);
        mThurs = (CustomToggleButton) findViewById(R.id.thurs);
        mFri = (CustomToggleButton) findViewById(R.id.fri);
        mSat = (CustomToggleButton) findViewById(R.id.sat);
        mSun = (CustomToggleButton) findViewById(R.id.sun);
        mQuantity = (EditText) findViewById(R.id.text_quantity);
        mDuration = (EditText) findViewById(R.id.duration);

        final Button button = findViewById(R.id.btn_save);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mQuantity.getText()) || TextUtils.isEmpty(mDuration.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else {
                    String medicine = mMedicine.getSelectedItem().toString();
                    String quantity = mQuantity.getText().toString();
                    String str_duration = mDuration.getText().toString();
                    Integer duration = Integer.parseInt(str_duration);
                    Integer hour = mTimePicker.getHour();
                    Integer minute = mTimePicker.getMinute();
                    Integer mon = mMon.isChecked() ? 1 : 0;
                    Integer tue = mTues.isChecked() ? 1 : 0;
                    Integer wed = mWed.isChecked() ? 1 : 0;
                    Integer thu = mThurs.isChecked() ? 1 : 0;
                    Integer fri = mFri.isChecked() ? 1 : 0;
                    Integer sat = mSat.isChecked() ? 1 : 0;
                    Integer sun = mSun.isChecked() ? 1 : 0;

                    replyIntent.putExtra(MEDICINE, medicine);
                    replyIntent.putExtra(QUANTITY,quantity);
                    replyIntent.putExtra(DURATION,duration);
                    replyIntent.putExtra(HOUR,hour);
                    replyIntent.putExtra(MINUTE,minute);
                    replyIntent.putExtra(MON,mon);
                    replyIntent.putExtra(TUES,tue);
                    replyIntent.putExtra(WEDNES,wed);
                    replyIntent.putExtra(THURS,thu);
                    replyIntent.putExtra(FRI,fri);
                    replyIntent.putExtra(SATUR,sat);
                    replyIntent.putExtra(SUN,sun);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();

            }
        });
    }



}