package com.example.studentsapp.cost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsapp.MainActivity;
import com.example.studentsapp.R;
import com.example.studentsapp.models.Student;
import com.example.studentsapp.repositories.LessonRepository;
import com.example.studentsapp.repositories.StudentRepository;

public class TotalCostCalculator extends AppCompatActivity {

    private int id;
    private TextView nameTextView,unPaidHoursTextView,totalCostTextView;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_cost_calculator);

        nameTextView = findViewById(R.id.total_cost_calculator_name);
        unPaidHoursTextView = findViewById(R.id.total_cost_calculator_unpaid_hours);
        totalCostTextView = findViewById(R.id.total_cost_calculator_total_cost);
        submitBtn = findViewById(R.id.total_cost_calculator_submit_btn);

        Intent intent = getIntent();
        id = intent.getIntExtra("student_id",0);
        StudentRepository studentRepository = new StudentRepository(this);
        Student student = studentRepository.getStudentById(id);
        setTitle(student.getName() + ", " + student.getStudentClass());

        printStudentsData(student);

        if (student.getUnPaidHours() == 0F) {
            submitBtn.setClickable(false);
        }
        else {
            submitBtn.setOnClickListener(view -> {
                LessonRepository lessonRepository = new LessonRepository(this,id);

                AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setTitle("Είσαι Σίγουρος;");
                dialog.setMessage("Αυτή η ενέργεια δεν μπορεί να ανακληθεί! Θέλετε να συνεχίσετε;");
                dialog.setButton(Dialog.BUTTON_POSITIVE, "ΝΑΙ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        studentRepository.updateStudentUnPaidHours(id, 0);
                        Student student = studentRepository.getStudentById(id);
                        printStudentsData(student);

                        lessonRepository.deleteAllLessons();

                        Toast.makeText(getApplicationContext(), "Τα στοιχεία ενημερώθηκαν!", Toast.LENGTH_LONG).show();
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

    private void printStudentsData(Student student){
        nameTextView.setText("Όνομα Μαθητή : " + student.getName());
        unPaidHoursTextView.setText("Σύνολο ωρών : " + student.getUnPaidHours());
        totalCostTextView.setText("Σύνολο χρημάτων : " + student.getCostPerHour() * student.getUnPaidHours() + " €");
    }

}