package com.example.studentsapp.models;

public class Student {

    private String name;
    private String studentClass;
    private String phone;
    private float totalHours,unPaidHours;
    private int id,costPerHour;
    private float totalCost;

    public Student(int id,String name, String studentClass, String phone, int costPerHour,float totalHours,float unPaidHours) {
        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
        this.phone = phone;
        this.costPerHour = costPerHour;
        this.totalHours = totalHours;
        this.unPaidHours = unPaidHours;
    }


    public int getId() {
        return id;
    }

    public float getUnPaidHours() {
        return unPaidHours;
    }

    public void setUnPaidHours(float unPaidHours) {
        this.unPaidHours = unPaidHours;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(float totalHours) {
        this.totalHours = totalHours;
    }

    public int getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(int costPerHour) {
        this.costPerHour = costPerHour;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
}
