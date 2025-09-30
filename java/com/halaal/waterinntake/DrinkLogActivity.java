package com.halaal.waterinntake;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.*;

public class DrinkLogActivity extends AppCompatActivity {

    private TextView txtTotalDrank, txtGoal;
    private ProgressBar progressBar;
    private Button btn150, btn200, btn250, btn300;
    private double totalToday = 0;

    private User currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;

    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_log);

        currentUser = SharedPrefManager.getUser(this);

        txtTotalDrank = findViewById(R.id.txtTotalDrank);
        txtGoal = findViewById(R.id.txtGoal);
        progressBar = findViewById(R.id.progressBar);

        btn150 = findViewById(R.id.btn150);
        btn200 = findViewById(R.id.btn200);
        btn250 = findViewById(R.id.btn250);
        btn300 = findViewById(R.id.btn300);

        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference("WaterLogs");

        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        loadTodayProgress();

        btn150.setOnClickListener(v -> logWater(150));
        btn200.setOnClickListener(v -> logWater(200));
        btn250.setOnClickListener(v -> logWater(250));
        btn300.setOnClickListener(v -> logWater(300));
    }

    private void loadTodayProgress() {
        totalToday = getSharedPreferences("WaterPrefs", MODE_PRIVATE)
                .getFloat(todayDate + "_total", 0f);
        updateUI();
    }

    private void logWater(int amountMl) {
        totalToday += amountMl / 1000.0; // convert ml to litre

        // Save locally
        getSharedPreferences("WaterPrefs", MODE_PRIVATE).edit()
                .putFloat(todayDate + "_total", (float) totalToday)
                .apply();

        // Save to Firebase
        userRef.child(currentUser.name).child(todayDate).setValue(totalToday);

        updateUI();
    }

    private void updateUI() {
        double goal = currentUser.dailyWaterGoal;
        txtGoal.setText("Goal: " + goal + " L");
        txtTotalDrank.setText("Drank: " + String.format(Locale.getDefault(), "%.2f", totalToday) + " L");

        progressBar.setMax((int) (goal * 100));
        progressBar.setProgress((int) (totalToday * 100));
    }
}
