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

public class TeacherAdapter extends ArrayAdapter<Teacher> {
    private Context context;
    private ArrayList<Teacher> teachers;

    public TeacherAdapter(@NonNull Context context, ArrayList<Teacher> teachers) {
        super(context, 0, teachers);
        this.context = context;
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.teacher_item_layout, parent, false);
        }

        Teacher teacher = teachers.get(position);
        TextView tvTeacherInfo = convertView.findViewById(R.id.tvTeacherInfo);

        // Format the teacher information
        String teacherInfo = "Mã GV: " + teacher.getCode() + "\n" +
                "Họ tên: " + teacher.getName() + "\n" +
                "Ngôn ngữ: " + teacher.getLanguage();

        tvTeacherInfo.setText(teacherInfo);

        return convertView;
    }
}
