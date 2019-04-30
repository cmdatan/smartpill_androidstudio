package com.example.smartpill;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class homeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, PopupMenu.OnMenuItemClickListener {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    public static homeActivity instance;
    public boolean connected;

    public WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Stetho.initializeWithDefaults(this);

        instance = this;
        connected = false;

        loadFragment(new dashboardFrag());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        FloatingActionButton mFloatingAction = (FloatingActionButton) findViewById(R.id.floating_action_button);
        mFloatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, addScheduleActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        Button btn = (Button) findViewById(R.id.menu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(homeActivity.this, v);
                popup.setOnMenuItemClickListener(homeActivity.this);
                popup.inflate(R.menu.menu);
                popup.show();
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                fragment = new dashboardFrag();
                break;

            case R.id.navigation_medicines:
                fragment = new medicinesFrag();
                break;

            case R.id.navigation_calendar:
                fragment = new calendarFrag();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.connect:
                ((btMaster) getApplicationContext()).pairDevice(item);
                return true;
            case R.id.logout:
                // do your code
                return true;
            case R.id.settings:
                // do your code
                return true;
            default:
                return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(addScheduleActivity.MEDICINE),
                    data.getStringExtra(addScheduleActivity.QUANTITY),
                    data.getIntExtra(addScheduleActivity.DURATION,0),
                    data.getIntExtra(addScheduleActivity.HOUR,0),
                    data.getIntExtra(addScheduleActivity.MINUTE,0),
                    data.getIntExtra(addScheduleActivity.MON,0),
                    data.getIntExtra(addScheduleActivity.TUES,0),
                    data.getIntExtra(addScheduleActivity.WEDNES,0),
                    data.getIntExtra(addScheduleActivity.THURS,0),
                    data.getIntExtra(addScheduleActivity.FRI,0),
                    data.getIntExtra(addScheduleActivity.SATUR,0),
                    data.getIntExtra(addScheduleActivity.SUN,0));
            if (word != null){
                mWordViewModel.insert(word);

                if(((btMaster) getApplicationContext()).mConnectedThread != null) { //First check to make sure thread created
                    ((btMaster) getApplicationContext()).mConnectedThread.write(String.valueOf(data.getIntExtra(addScheduleActivity.DURATION,0)));      //boxNo
                    ((btMaster) getApplicationContext()).mConnectedThread.write("-");
                    ((btMaster) getApplicationContext()).mConnectedThread.write(String.valueOf(data.getIntExtra(addScheduleActivity.MON,0)));      //dow
                    ((btMaster) getApplicationContext()).mConnectedThread.write("-");
                    ((btMaster) getApplicationContext()).mConnectedThread.write(String.valueOf(data.getIntExtra(addScheduleActivity.TUES,0)));      //dow
                    ((btMaster) getApplicationContext()).mConnectedThread.write("-");
                    ((btMaster) getApplicationContext()).mConnectedThread.write(String.valueOf(data.getIntExtra(addScheduleActivity.WEDNES,0)));      //dow
                    ((btMaster) getApplicationContext()).mConnectedThread.write("-");
                    ((btMaster) getApplicationContext()).mConnectedThread.write(String.valueOf(data.getIntExtra(addScheduleActivity.THURS,0)));      //dow
                    ((btMaster) getApplicationContext()).mConnectedThread.write("-");
                    ((btMaster) getApplicationContext()).mConnectedThread.write(String.valueOf(data.getIntExtra(addScheduleActivity.FRI,0)));      //dow
                    ((btMaster) getApplicationContext()).mConnectedThread.write("-");
                    ((btMaster) getApplicationContext()).mConnectedThread.write(String.valueOf(data.getIntExtra(addScheduleActivity.SATUR,0)));      //dow
                    ((btMaster) getApplicationContext()).mConnectedThread.write("-");
                    ((btMaster) getApplicationContext()).mConnectedThread.write(String.valueOf(data.getIntExtra(addScheduleActivity.SUN,0)));      //dow
                    ((btMaster) getApplicationContext()).mConnectedThread.write("-");
                    ((btMaster) getApplicationContext()).mConnectedThread.write(String.valueOf(data.getIntExtra(addScheduleActivity.HOUR,0)));     //hr
                    ((btMaster) getApplicationContext()).mConnectedThread.write("-");
                    ((btMaster) getApplicationContext()).mConnectedThread.write(String.valueOf(data.getIntExtra(addScheduleActivity.MINUTE,0)));      //min
                    ((btMaster) getApplicationContext()).mConnectedThread.write("#");              //end
                }
            }

            /*Toast.makeText(
                    this.getApplicationContext(),
                    "Saved.",
                    Toast.LENGTH_LONG).show();*/
        } else {
            Toast.makeText(
                    this.getApplicationContext(),
                    "Cancelled.",
                    Toast.LENGTH_LONG).show();
        }
    }

}


