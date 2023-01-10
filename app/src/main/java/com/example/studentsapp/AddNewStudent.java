package com.example.studentsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentsapp.models.Student;
import com.example.studentsapp.repositories.StudentRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddNewStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ArrayList<Student> students = new ArrayList<>();
    private EditText name,phone,costPerHour;
    private Spinner studentClassSpinner;
    private Button submitBtn;
    private String[] classes = new String[]{
            "Α Δημοτικού" , "Β Δημοτικού" , "Γ Δημοτικού" ,
            "Δ Δημοτικού" , "Ε Δημοτικού" , "ΣΤ Δημοτικού" ,
            "Α Γυμνασίου" , "Β Γυμνασίου" , "Γ Γυμνασίου" ,
            "Α Λυκείου" , "Β Λυκείου" , "Γ Λυκείου" ,
    };
    private String studentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);

        name = findViewById(R.id.create_student_name);
        phone = findViewById(R.id.create_student_phone);
        costPerHour = findViewById(R.id.create_student_cost_per_hour);
        submitBtn = findViewById(R.id.create_student_btn);

        studentClassSpinner = findViewById(R.id.classes_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentClassSpinner.setAdapter(adapter);
        studentClassSpinner.setOnItemSelectedListener(this);

        StudentRepository studentRepository = new StudentRepository(this);

        SharedPreferences preferences = getApplication().getSharedPreferences("students_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        submitBtn.setOnClickListener(view -> {

            if(!name.getText().toString().isEmpty() && !costPerHour.getText().toString().isEmpty() && !studentClass.isEmpty()){

                int maxId = preferences.getInt("maxId" , 0);

                    maxId++;
                    Student student = new Student(
                            maxId,
                            name.getText().toString(),
                            studentClass,
                            phone.getText().toString(),
                            Integer.valueOf(costPerHour.getText().toString()),
                            0,
                            0
                    );

                    studentRepository.saveStudent(student,maxId);

                Toast.makeText(this,"Ο μαθητής " + student.getName() + " της " + student.getStudentClass() + " προστέθηκε επιτυχώς" ,Toast.LENGTH_LONG).show();

                Intent myIntent = new Intent(this, MainActivity.class);
                this.startActivity(myIntent);

            }
            else {
                Toast.makeText(this,"Πρέπει να συμπληρωθούν όλα τα πεδία για να ολοκληρωθεί η ενέργεια" ,Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch(position) {
            case 0:
                studentClass = classes[0];
                break;
            case 1:
                studentClass = classes[1];
                break;
            case 2:
                studentClass = classes[2];
                break;
            case 3:
                studentClass = classes[3];
                break;
            case 4:
                studentClass = classes[4];
                break;
            case 5:
                studentClass = classes[5];
                break;
            case 6:
                studentClass = classes[6];
                break;
            case 7:
                studentClass = classes[7];
                break;
            case 8:
                studentClass = classes[8];
                break;
            case 9:
                studentClass = classes[9];
                break;
            case 10:
                studentClass = classes[10];
                break;
            case 11:
                studentClass = classes[11];
                break;
            case 12:
                studentClass = classes[12];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        studentClass = classes[0];
    }
}