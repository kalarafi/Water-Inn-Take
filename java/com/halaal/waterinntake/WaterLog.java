package com.halaal.waterinntake;

public class WaterLog {
    private String date;
    private int intake;

    public WaterLog() {}

    public WaterLog(String date, int intake) {
        this.date = date;
        this.intake = intake;
    }

    public String getDate() { return date; }
    public int getIntake() { return intake; }
}