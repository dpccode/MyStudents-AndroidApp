package com.example.studentsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.studentsapp.cost.TotalCostCalculator;
import com.example.studentsapp.hours.AddHour;
import com.example.studentsapp.models.Student;
import com.example.studentsapp.repositories.StudentRepository;

public class MyStudentsMenu extends AppCompatActivity {

    private ImageView addHour,costCalculatorMenu,deleteStudent,editStudent,historyMenu;
    private String name,studentClass;
    private int id;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_students_menu);

        addHour = findViewById(R.id.add_hour_menu_btn);
        costCalculatorMenu = findViewById(R.id.cost_calculator_menu_btn);
        deleteStudent = findViewById(R.id.delete_student_btn);
        editStudent = findViewById(R.id.edit_student_menu_btn);
        historyMenu = findViewById(R.id.student_lessons_history_btn);

        StudentRepository studentRepository = new StudentRepository(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("student_id",0);
        name = intent.getStringExtra("student_name");
        studentClass = intent.getStringExtra("student_class");

        setTitle(name + ",\n" + studentClass);

        addHour.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, AddHour.class);
            myIntent.putExtra("student_id",id);
            this.startActivity(myIntent);
        });

        costCalculatorMenu.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, TotalCostCalculator.class);
            myIntent.putExtra("student_id",id);
            this.startActivity(myIntent);
        });

        editStudent.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, EditStudent.class);
            myIntent.putExtra("student_id",id);
            this.startActivity(myIntent);
        });

        historyMenu.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, History.class);
            myIntent.putExtra("student_id",id);
            this.startActivity(myIntent);
        });

        final Context context = this;

        deleteStudent.setOnClickListener(view -> {

            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Είσαι Σίγουρος;");
            dialog.setMessage("Αυτή η ενέργεια δεν μπορεί να ανακληθεί! Θέλετε να συνεχίσετε;");
            dialog.setButton(Dialog.BUTTON_POSITIVE, "ΝΑΙ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    studentRepository.deleteStudent(id);
                    Toast.makeText(getApplicationContext(),"Ο Μαθητής διαγράγηκε επιτυχώς",Toast.LENGTH_LONG).show();

                    Intent myIntent = new Intent(context, MainActivity.class);
                    context.startActivity(myIntent);
                }
            });

            dialog.setButton(Dialog.BUTTON_NEGATIVE, Html.fromHtml("<font color='#C50000'>ΑΚΥΡΟ</font>"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        });

    }
}