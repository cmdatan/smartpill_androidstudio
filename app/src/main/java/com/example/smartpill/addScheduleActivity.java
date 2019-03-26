package com.example.smartpill;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

public class addScheduleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addschedule);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }

        String[] arraySpinner = new String[] {
                "day(s)", "week(s)", "month(s)", "year(s)"
        };
        Spinner s = (Spinner) findViewById(R.id.duration);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

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
                startActivity(new Intent(addScheduleActivity.this, homeActivity.class));;
            }
        });

    }

}