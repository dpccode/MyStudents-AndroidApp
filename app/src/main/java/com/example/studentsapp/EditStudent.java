package com.example.studentsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsapp.models.Student;
import com.example.studentsapp.repositories.StudentRepository;

public class EditStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button editNameBtn,editPhoneBtn,editClassButton,editCostPerHourBtn,submitBtn;
    private TextView nameTextView,phoneTextView,classTextView,costTextView;
    private EditText nameEditText,phoneEditText,costEditText;
    private Spinner classSpinner;
    private int id;
    private String studentClass;
    private String[] classes = new String[]{
            "Α Δημοτικού" , "Β Δημοτικού" , "Γ Δημοτικού" ,
            "Δ Δημοτικού" , "Ε Δημοτικού" , "ΣΤ Δημοτικού" ,
            "Α Γυμνασίου" , "Β Γυμνασίου" , "Γ Γυμνασίου" ,
            "Α Λυκείου" , "Β Λυκείου" , "Γ Λυκείου" ,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        setTitle("Επεξεργασία στοιχείων μαθητή");

        editNameBtn = findViewById(R.id.edit_student_edit_name_btn);
        editPhoneBtn = findViewById(R.id.edit_student_edit_phone_btn);
        editClassButton = findViewById(R.id.edit_student_edit_class_btn);
        editCostPerHourBtn = findViewById(R.id.edit_student_edit_cost_per_hour_btn);
        submitBtn = findViewById(R.id.edit_student_edit_submit_btn);

        nameTextView = findViewById(R.id.edit_student_edit_name_textView);
        phoneTextView = findViewById(R.id.edit_student_edit_phone_textView);
        classTextView = findViewById(R.id.edit_student_edit_class_textView);
        costTextView =  findViewById(R.id.edit_student_edit_cost_textView);

        nameEditText = findViewById(R.id.edit_student_edit_name_editText);
        phoneEditText = findViewById(R.id.edit_student_edit_phone_editText);
        classSpinner = findViewById(R.id.edit_student_edit_class_spinner);
        costEditText = findViewById(R.id.edit_student_edit_cost_editText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);
        classSpinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("student_id",0);

        StudentRepository studentRepository = new StudentRepository(this);
        Student student = studentRepository.getStudentById(id);
        nameTextView.setText("Όνομα: " + student.getName());
        if (!student.getPhone().isEmpty())
            phoneTextView.setText(student.getPhone());
        classTextView.setText("Τάξη: " + student.getStudentClass());
        costTextView.setText("Κόστος ανά ώρα: " + Float.toString(student.getCostPerHour()) + " €");


        editNameBtn.setOnClickListener(view -> {
            chooseView(nameTextView,nameEditText);
        });

        editPhoneBtn.setOnClickListener(view -> {
            chooseView(phoneTextView,phoneEditText);
        });

        editCostPerHourBtn.setOnClickListener(view -> {
            chooseView(costTextView,costEditText);
        });

        editClassButton.setOnClickListener(view -> {
            if (classTextView.getVisibility() == View.VISIBLE) {
                classTextView.setVisibility(View.GONE);
                classSpinner.setVisibility(View.VISIBLE);
            }else{
                classTextView.setVisibility(View.VISIBLE);
                classSpinner.setVisibility(View.GONE);
            }
        });

        submitBtn.setOnClickListener(view -> {
            Student updatedStudent = student;

            if(!nameEditText.getText().toString().isEmpty())
                updatedStudent.setName(nameEditText.getText().toString());

            if(!phoneEditText.getText().toString().isEmpty())
                updatedStudent.setPhone(phoneEditText.getText().toString());

            if(!costEditText.getText().toString().isEmpty())
                updatedStudent.setCostPerHour(Integer.valueOf(costEditText.getText().toString()));

            if(classSpinner.getVisibility() == View.VISIBLE)
                updatedStudent.setStudentClass(studentClass);

            final Context context = this;
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Ανανέωση στοιχείων μαθητή");
            dialog.setButton(Dialog.BUTTON_POSITIVE, "ΝΑΙ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    studentRepository.updateStudent(updatedStudent,student.getId());
                    Toast.makeText(context,"Τα στοιχεία ενημερώθηκαν!",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(context,MyStudentsMenu.class);
                    intent1.putExtra("student_id",student.getId());
                    intent1.putExtra("student_name",student.getName());
                    intent1.putExtra("student_class",student.getStudentClass());
                    startActivity(intent1);
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

    private void chooseView(TextView textView,EditText editText){
        if (textView.getVisibility() == View.VISIBLE) {
            textView.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
        }
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