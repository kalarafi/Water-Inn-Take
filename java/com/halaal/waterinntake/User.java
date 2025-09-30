package com.halaal.waterinntake;

public class User {
    public String name, gender;
    public int age, height, weight;
    public double dailyWaterGoal;

    public User() {} // Firebase needs this

    public User(String name, String gender, int age, int height, int weight, double dailyWaterGoal) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.dailyWaterGoal = dailyWaterGoal;
    }
}

