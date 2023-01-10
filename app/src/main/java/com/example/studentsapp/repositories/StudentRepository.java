package com.example.studentsapp.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.studentsapp.models.Student;

import java.util.ArrayList;

public class StudentRepository {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public StudentRepository(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("students_data", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public void saveStudent(Student student,int maxId){

        editor.putInt("maxId",maxId).commit();
        editor.putString("name"+String.valueOf(maxId),student.getName()).commit();
        editor.putString("phone"+String.valueOf(maxId),student.getPhone()).commit();
        editor.putString("studentClass"+String.valueOf(maxId),student.getStudentClass()).commit();
        editor.putInt("costPerHour"+String.valueOf(maxId),student.getCostPerHour()).commit();
        editor.putFloat("totalHours"+String.valueOf(maxId),student.getTotalHours()).commit();
        editor.putFloat("unPaidHours"+String.valueOf(maxId),student.getUnPaidHours()).commit();
        editor.apply();

    }


    public ArrayList<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<>();

        int maxId = preferences.getInt("maxId" , 0);

        for(int i=1; i <= maxId; i++){

            String name = preferences.getString("name"+String.valueOf(i),null);
            if(name != null) {
                String phone = preferences.getString("phone" +String.valueOf(i), null);
                String studentClass = preferences.getString("studentClass" + String.valueOf(i), null);
                int costPerHour = preferences.getInt("costPerHour" + String.valueOf(i), 0);
                Float totalHours = preferences.getFloat("totalHours"+ String.valueOf(i),0);
                Float unPaidHours = preferences.getFloat("unPaidHours"+ String.valueOf(i),0);

                Student student = new Student(
                        i,
                        name,
                        studentClass,
                        phone,
                        costPerHour,
                        totalHours,
                        unPaidHours
                );
                students.add(student);
            }
        }
        return students;
    }

    public Student getStudentById(int id){
        String name = preferences.getString("name"+String.valueOf(id),null);
        if(name != null) {
            String phone = preferences.getString("phone" +String.valueOf(id), null);
            String studentClass = preferences.getString("studentClass" + String.valueOf(id), null);
            int costPerHour = preferences.getInt("costPerHour" + String.valueOf(id), 0);
            Float totalHours = preferences.getFloat("totalHours"+ String.valueOf(id),0);
            Float unPaidHours = preferences.getFloat("unPaidHours"+ String.valueOf(id),0);

            Student student = new Student(
                    id,
                    name,
                    studentClass,
                    phone,
                    costPerHour,
                    totalHours,
                    unPaidHours
            );
            return student;
        }else {
            return null;
        }
    }


    public void updateStudent(Student student,int id){
        editor.putString("name"+String.valueOf(id),student.getName()).commit();
        editor.putString("phone"+String.valueOf(id),student.getPhone()).commit();
        editor.putString("studentClass"+String.valueOf(id),student.getStudentClass()).commit();
        editor.putInt("costPerHour"+String.valueOf(id),student.getCostPerHour()).commit();
    }

    public void updateStudentUnPaidHours(int id,float unPaidHours){
        editor.putFloat("unPaidHours"+String.valueOf(id),unPaidHours).commit();
        editor.apply();
    }

    public void deleteStudent(int id){
        editor.remove("name"+String.valueOf(id));
        editor.remove("phone"+String.valueOf(id));
        editor.remove("studentClass"+String.valueOf(id));
        editor.remove("costPerHour"+String.valueOf(id));
        editor.remove("totalHours"+String.valueOf(id));
        editor.remove("unPaidHours"+String.valueOf(id));
        editor.apply();
    }

}
