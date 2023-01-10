package com.example.studentsapp.models;

public class Lesson {

    private int id;
    private String date;
    private float hours;
    private float cost;

    public Lesson(int id,String date, float hours, float cost) {
        this.date = date;
        this.hours = hours;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
