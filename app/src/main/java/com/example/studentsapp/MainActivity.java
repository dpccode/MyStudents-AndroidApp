package com.example.studentsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView addNewStudent,myStudents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Κεντρικό Μενού");

        addNewStudent = findViewById(R.id.newStudentBtn);
        myStudents = findViewById(R.id.myStudentsBtn);

        addNewStudent.setOnClickListener((view -> {
            Intent myIntent = new Intent(this, AddNewStudent.class);
            this.startActivity(myIntent);
                }));

        myStudents.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, MyStudents.class);
            this.startActivity(myIntent);
        });


    }
}