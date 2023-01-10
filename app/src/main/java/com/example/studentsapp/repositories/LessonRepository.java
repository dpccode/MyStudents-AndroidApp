package com.example.studentsapp.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.studentsapp.models.Lesson;

public class LessonRepository {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public LessonRepository(Context context, int id) {
        this.context = context;
        preferences = context.getSharedPreferences("student_lesson_history"+String.valueOf(id), Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveLesson(Lesson lesson,int maxId){

        editor.putInt("history_lesson_max_id", maxId);
        editor.putString("lesson_date"+String.valueOf(maxId),lesson.getDate()).commit();
        editor.putFloat("lesson_hours"+String.valueOf(maxId), lesson.getHours()).commit();
        editor.putFloat("lesson_cost"+String.valueOf(maxId), lesson.getCost()).commit();
        editor.apply();
    }

    public int getMaxId(){
        return preferences.getInt("history_lesson_max_id",0);
    }

    public void setMaxId(int maxId){
        editor.putInt("history_lesson_max_id",maxId);
    }

    public Lesson getLesson(int id){

        Lesson lesson = new Lesson(
                id,
                preferences.getString("lesson_date"+String.valueOf(id),null),
                preferences.getFloat("lesson_hours"+String.valueOf(id),0F),
                preferences.getFloat("lesson_cost"+String.valueOf(id),0F)
        );

        return lesson;
    }

    public void deleteLesson(int id){
        editor.remove("lesson_date" + String.valueOf(id)).commit();
        editor.remove("lesson_hours" + String.valueOf(id)).commit();
        editor.remove("lesson_cost" + String.valueOf(id)).commit();
        editor.apply();
    }

    public void deleteAllLessons(){

        for (int i = 0; i<= getMaxId(); i++){
            deleteLesson(i);
        }

    }


}
