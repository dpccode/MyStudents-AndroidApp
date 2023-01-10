package com.example.studentsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentsapp.hours.AddHour;
import com.example.studentsapp.repositories.StudentRepository;
import com.example.studentsapp.models.Student;

import java.util.ArrayList;

public class MyStudents extends AppCompatActivity {

    private LinearLayout studentsListView;
    private ArrayList<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_students);
        setTitle("Οι μαθητές μους");

        studentsListView = findViewById(R.id.students_list);

        SharedPreferences preferences = getApplication().getSharedPreferences("students_data", Context.MODE_PRIVATE);

        StudentRepository studentRepository = new StudentRepository(this);
        students =  studentRepository.getStudents();

        for(int i=0; i <students.size(); i++){
            final View resView = getLayoutInflater().inflate(R.layout.my_students_item, null, false);
            LinearLayout card = resView.findViewById(R.id.add_hour_card);
            TextView textView = resView.findViewById(R.id.add_hour_text_view);

            final int finalI = i;
            card.setOnClickListener(view -> {

                Intent intent = new Intent(this, MyStudentsMenu.class);
                intent.putExtra("student_id",students.get(finalI).getId());
                intent.putExtra("student_name",students.get(finalI).getName());
                intent.putExtra("student_class",students.get(finalI).getStudentClass());

                startActivity(intent);
            });

            textView.setText(students.get(i).getName()+" " + students.get(i).getStudentClass());
            studentsListView.addView(resView);
        }
    }
}