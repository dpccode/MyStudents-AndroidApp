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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsapp.models.Lesson;
import com.example.studentsapp.models.Student;
import com.example.studentsapp.repositories.LessonRepository;
import com.example.studentsapp.repositories.StudentRepository;

public class History extends AppCompatActivity {

    private int studentsId;
    private int historyRecords;
    private LinearLayout historyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle("Ιστορικό μαθημάτων");

        historyListView = findViewById(R.id.lessons_history_list_layout);

        Intent intent = getIntent();
        studentsId = intent.getIntExtra("student_id",0);

        LessonRepository lessonRepository = new LessonRepository(this,studentsId);
        historyRecords = lessonRepository.getMaxId();

        for(int i = historyRecords; i>0; i--){

            Lesson lesson = lessonRepository.getLesson(i);

            final float finalLessonHours = lesson.getHours();
            final int finalI = i;
            final Context context = this;

            final View resView = getLayoutInflater().inflate(R.layout.history_item, null, false);
            TextView dateTextView = resView.findViewById(R.id.history_item_date);
            TextView hoursTextView = resView.findViewById(R.id.history_item_hours);
            TextView costTextView = resView.findViewById(R.id.history_item_cost);
            ImageButton deleteBtn = resView.findViewById(R.id.history_item_delete);

                if(lesson.getDate()!=null) {
                    dateTextView.setText("Ημερομηνία: " + lesson.getDate());
                    hoursTextView.setText("Ώρες: " + lesson.getHours());
                    costTextView.setText("Κόστος μαθήματος: " + lesson.getCost() + " €");



                    deleteBtn.setOnClickListener(view -> {
                        AlertDialog dialog = new AlertDialog.Builder(this).create();
                        dialog.setTitle("Είσαι Σίγουρος;");
                        dialog.setMessage("Διαγράφωντας αυτή την καταχώρηση θα σβηστούν " + finalLessonHours + " ώρες από το σύνολο των απλήρωτων ωρών");
                        dialog.setButton(Dialog.BUTTON_POSITIVE, "ΝΑΙ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                lessonRepository.deleteLesson(finalI);

                                StudentRepository studentRepository = new StudentRepository(context);
                                Student student = studentRepository.getStudentById(studentsId);
                                studentRepository.updateStudentUnPaidHours(
                                        studentsId,
                                        student.getUnPaidHours() - finalLessonHours);

                                historyListView.removeView(resView);
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
                    historyListView.addView(resView);
                }
            }
    }
}