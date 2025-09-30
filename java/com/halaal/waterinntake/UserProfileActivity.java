package com.halaal.waterinntake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {

    private EditText edtName, edtGender, edtAge, edtHeight, edtWeight;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView adviceTextView = findViewById(R.id.adviceTextView);

        int currentTemp = 34; // This could be user-input or based on known regional climate
        String advice = HydrationAdviceUtil.getAdviceForTemperature(currentTemp);
        adviceTextView.setText(advice);

        edtName = findViewById(R.id.edtName);
        edtGender = findViewById(R.id.edtGender);
        edtAge = findViewById(R.id.edtAge);
        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);
        btnUpdate = findViewById(R.id.btnUpdate);

        loadUserData();

        btnUpdate.setOnClickListener(v -> updateUserData());
    }

    private void loadUserData() {
        User user = SharedPrefManager.getUser(this);
        if (user != null) {
            edtName.setText(user.name);
            edtGender.setText(user.gender);
            edtAge.setText(String.valueOf(user.age));
            edtHeight.setText(String.valueOf(user.height));
            edtWeight.setText(String.valueOf(user.weight));
        }
    }

    private void updateUserData() {
        String name = edtName.getText().toString();
        String gender = edtGender.getText().toString();
        int age = Integer.parseInt(edtAge.getText().toString());
        double heightInFeet = Double.parseDouble(edtHeight.getText().toString());
        int height = (int) (heightInFeet * 30.48); // Convert to cm
        int weight = Integer.parseInt(edtWeight.getText().toString());

        double goal = WaterUtils.calculateWaterGoal(gender, age, height, weight);

        User updatedUser = new User(name, gender, age, height, weight, goal);
        SharedPrefManager.saveUser(this, updatedUser);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}