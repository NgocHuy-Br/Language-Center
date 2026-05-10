package com.example.language_center;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context context;
    private ArrayList<Student> students;

    public StudentAdapter(@NonNull Context context, ArrayList<Student> students) {
        super(context, 0, students);
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.student_item_layout, parent, false);
        }

        Student student = students.get(position);
        TextView tvStudentInfo = convertView.findViewById(R.id.tvStudentInfo);

        // Format the student information according to requirements
        String studentInfo = "Mã SV: " + student.getCode() + "\n" +
                "Họ tên: " + student.getName() + "\n" +
                "Trình độ: " + student.getLevel() + "\n" +
                "Lớp: " + student.getClassName();

        tvStudentInfo.setText(studentInfo);

        return convertView;
    }
}
