package com.example.smartpill;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

public class medupActivity extends AppCompatActivity {

    public static final String MEDICINE = "medicine";

    int position;
    int SID;
    String medicine;
    String boxNo;

    public MedsViewModel mMedsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_medupdate);

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
        //getIncomingIntent();

        mMedsViewModel = ViewModelProviders.of(homeActivity.instance).get(MedsViewModel.class);

        Button btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent replyIntent = new Intent();

                /*
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
                */
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
            boxNo = getIntent().getStringExtra("boxNo");

            setPopup(SID, medicine, boxNo);
        }
    }

    private void setPopup(int SID, String medicine, String boxNo) {
        TextView popup_text = findViewById(R.id.popup_text);
        popup_text.setText(medicine);

        TextView popup_box = findViewById(R.id.popup_box);
        popup_box.setText("Box " + boxNo);

    }

}
