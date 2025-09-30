package com.halaal.waterinntake;


import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class ReportActivity extends AppCompatActivity {

    private TextView txtTodayIntake, txtPastSummary;
    private User currentUser;
    private DatabaseReference userRef;
    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        txtTodayIntake = findViewById(R.id.txtTodayIntake);
        txtPastSummary = findViewById(R.id.txtPastSummary);

        currentUser = SharedPrefManager.getUser(this);
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        userRef = FirebaseDatabase.getInstance().getReference("WaterLogs").child(currentUser.name);

        loadTodayData();
        loadPastData();
    }

    private void loadTodayData() {
        userRef.child(todayDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double value = snapshot.getValue(Double.class);
                if (value != null) {
                    txtTodayIntake.setText("Today's Intake: " + String.format(Locale.getDefault(), "%.2f", value) + " L");
                } else {
                    txtTodayIntake.setText("Today's Intake: 0.00 L");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txtTodayIntake.setText("Error loading today's data");
            }
        });
    }

    private void loadPastData() {
        userRef.orderByKey().limitToLast(7).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder summary = new StringBuilder();
                for (DataSnapshot entry : snapshot.getChildren()) {
                    String date = entry.getKey();
                    Double litres = entry.getValue(Double.class);
                    summary.append(date).append(": ").append(String.format(Locale.getDefault(), "%.2f", litres)).append(" L\n");
                }
                txtPastSummary.setText(summary.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txtPastSummary.setText("Error loading history");
            }
        });
    }
}
