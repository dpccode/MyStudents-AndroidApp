package com.example.studentsapp.hours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsapp.MainActivity;
import com.example.studentsapp.R;
import com.example.studentsapp.models.Lesson;
import com.example.studentsapp.models.Student;
import com.example.studentsapp.repositories.LessonRepository;
import com.example.studentsapp.repositories.StudentRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddHour extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner hoursSpinner;
    private Button button;
    private TextView title;
    private String name,studentClass;
    private float totalHours,unPaidHours;
    private int id;
    private float costPerHour,hoursToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hour);

        hoursSpinner = findViewById(R.id.hours_spinner);
        button = findViewById(R.id.add_hour_btn);
        title = findViewById(R.id.add_hour_title_text_view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hours, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hoursSpinner.setBackgroundColor(0);
        hoursSpinner.setAdapter(adapter);
        hoursSpinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("student_id",0);
        getAndPrintStudentsData(id);

        button.setOnClickListener(view -> {

            StudentRepository studentRepository = new StudentRepository(this);

            DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
            String date = dateFormat.format(Calendar.getInstance().getTime());

            LessonRepository lessonRepository = new LessonRepository(this,id);
            int lessonId = lessonRepository.getMaxId();

            Lesson lesson = new Lesson(
                    lessonId,
                    date,
                    hoursToAdd,
                    hoursToAdd * costPerHour);


            lessonRepository.saveLesson(lesson,lessonRepository.getMaxId()+1);

            studentRepository.updateStudentUnPaidHours(id,unPaidHours + hoursToAdd);

            Toast.makeText(this,"Τα στοιχεία ενημερώθηκαν!",Toast.LENGTH_LONG).show();
            finish();
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) adapterView.getChildAt(0)).setTextSize(20);

        switch (position){
            case 0:
                hoursToAdd = 1.0F;
                break;
            case 1:
                hoursToAdd = 1.5F;
                break;
            case 2:
                hoursToAdd = 2F;
                break;
            case 3:
                hoursToAdd = 2.5F;
                break;
            case 4:
                hoursToAdd = 3F;
                break;
            case 5:
                hoursToAdd = 0.5F;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getAndPrintStudentsData(int id){
        StudentRepository studentRepository = new StudentRepository(this);
        Student student = studentRepository.getStudentById(id);

        if(student != null){
            name = studentRepository.getStudentById(id).getName();
            studentClass = studentRepository.getStudentById(id).getStudentClass();
            costPerHour = studentRepository.getStudentById(id).getCostPerHour();
            totalHours = studentRepository.getStudentById(id).getTotalHours();
            unPaidHours = studentRepository.getStudentById(id).getUnPaidHours();
        }


        title.setText("O Μαθητής " + name + " της " + studentClass + " έχει κανει " + unPaidHours
                + " ώρες μάθημα από την προηγούμενη πληρωμή και χρωαστάει "
                + unPaidHours * costPerHour + " €");
    }

}