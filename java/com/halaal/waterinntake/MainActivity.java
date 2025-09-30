package com.halaal.waterinntake;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private TextView totalIntakeText, recommendedIntakeText;
    private ProgressBar waterProgressBar;
    private int totalIntake = 0;
    private int recommendedIntake = 0;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    Button btn150ml, btn200ml, btn250ml, btn300ml, btnReminder, btnReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReminderUtils.scheduleHydrationReminder(this);
        setContentView(R.layout.activity_main);

        totalIntakeText = findViewById(R.id.totalIntakeText);
        recommendedIntakeText = findViewById(R.id.recommendedIntakeText);
        waterProgressBar = findViewById(R.id.waterProgressBar);
        btn150ml = findViewById(R.id.btn150ml);
        btn200ml = findViewById(R.id.btn200ml);
        btn250ml = findViewById(R.id.btn250ml);
        btn300ml = findViewById(R.id.btn300ml);
        btnReminder = findViewById(R.id.btnReminder);
        btnReport = findViewById(R.id.btnReport);

        db = FirebaseUtils.getFirestore();
        mAuth = FirebaseUtils.getAuth();

        loadUserProfile();
        loadTodayIntake();
        setupButtons();
    }

    private void setupButtons() {
        btn150ml.setOnClickListener(v -> addWaterIntake(150));
        btn200ml.setOnClickListener(v -> addWaterIntake(200));
        btn250ml.setOnClickListener(v -> addWaterIntake(250));
        btn300ml.setOnClickListener(v -> addWaterIntake(300));

        btnReminder.setOnClickListener(v -> startActivity(new Intent(this, ReminderActivity.class)));
        btnReport.setOnClickListener(v -> startActivity(new Intent(this, ReportActivity.class)));
    }

    private void addWaterIntake(int ml) {
        totalIntake += ml;
        saveToFirebase();
        updateUI();
    }

    private void updateUI() {
        totalIntakeText.setText("Today's Intake: " + totalIntake + " ml");
        if (recommendedIntake != 0) {
            waterProgressBar.setProgress((int) (((float) totalIntake / recommendedIntake) * 100));
        } else {
            waterProgressBar.setProgress(0);
        }
    }

    private void loadUserProfile() {
        String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : null;

        if (userId == null) {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        int weight = documentSnapshot.getLong("weight").intValue();
                        recommendedIntake = (int)(weight * 33); // 33ml per kg
                        recommendedIntakeText.setText("Recommended: " + recommendedIntake + " ml");
                        waterProgressBar.setMax(recommendedIntake);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error loading user data", Toast.LENGTH_SHORT).show());
    }

    private void loadTodayIntake() {
        totalIntake = 0; // Placeholder, use Room DB later
        updateUI();
    }

    private void saveToFirebase() {
        String userId = mAuth.getCurrentUser().getUid();
        String today = java.time.LocalDate.now().toString();

        db.collection("water_logs")
                .document(userId + "_" + today)
                .update("intake", totalIntake)
                .addOnFailureListener(e -> db.collection("water_logs")
                        .document(userId + "_" + today)
                        .set(new WaterLog(today, totalIntake)));
    }
}
