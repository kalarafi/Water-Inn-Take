package com.halaal.waterinntake;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WaterTrackerActivity extends AppCompatActivity {

    private TextView waterTotalText;
    private Button btn150, btn200, btn250, btn300;
    private double totalIntake = 0;
    private DatabaseReference waterRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tracker);

        waterTotalText = findViewById(R.id.waterTotalText);
        btn150 = findViewById(R.id.btn150);
        btn200 = findViewById(R.id.btn200);
        btn250 = findViewById(R.id.btn250);
        btn300 = findViewById(R.id.btn300);

        User user = SharedPrefManager.getUser(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        waterRef = database.getReference("Intake").child(user.name);

        View.OnClickListener listener = view -> {
            int amount = Integer.parseInt(((Button) view).getText().toString().replace("ml", ""));
            totalIntake += amount / 1000.0; // convert to litres
            waterTotalText.setText(String.format("%.2f Litres", totalIntake));
            waterRef.push().setValue(totalIntake);
        };

        btn150.setOnClickListener(listener);
        btn200.setOnClickListener(listener);
        btn250.setOnClickListener(listener);
        btn300.setOnClickListener(listener);
    }
}