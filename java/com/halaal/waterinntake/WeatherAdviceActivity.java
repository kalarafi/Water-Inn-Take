package com.halaal.waterinntake;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherAdviceActivity extends AppCompatActivity {

    private TextView txtAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_advice);

        txtAdvice = findViewById(R.id.txtAdvice);
        showAdviceList();
    }

    private void showAdviceList() {
        String advice = "💧 **Recommended Daily Water Intake by Temperature:**\n\n" +
                "🌡 0°C – 10°C:\n  ➤ 2.0 to 2.5 litres\n\n" +
                "🌡 11°C – 20°C:\n  ➤ 2.5 to 3.0 litres\n\n" +
                "🌡 21°C – 30°C:\n  ➤ 3.0 to 3.5 litres\n\n" +
                "🌡 31°C – 40°C:\n  ➤ 3.5 to 4.0 litres\n\n" +
                "🌡 41°C – 50°C:\n  ➤ 4.0 to 5.0 litres\n\n" +
                "📝 Tip: Increase intake if you're active, sweating, or sick.";

        txtAdvice.setText(advice);
    }
}