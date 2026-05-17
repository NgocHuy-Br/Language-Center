package com.example.language_center;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;


public class TeacherAdapter extends ArrayAdapter<Teacher> {
    private Context context;
    private ArrayList<Teacher> teachers;
    private DatabaseHelper databaseHelper;


    public TeacherAdapter(@NonNull Context context, ArrayList<Teacher> teachers) {
        super(context, 0, teachers);
        this.context = context;
        this.teachers = teachers;
        this.databaseHelper = new DatabaseHelper(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.teacher_item_layout, parent, false);
        }

        Teacher teacher = teachers.get(position);

        // mapping
        TextView tvTeacherInfo = (TextView) convertView.findViewById(R.id.tvTeacherInfo);
        Button btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);


        String teacherInfo = "Mã số: " + teacher.getCode() + "\n" +
                "Họ tên: " + teacher.getName() + "\n" +
                "Ngôn ngữ: " + teacher.getLanguage();
        tvTeacherInfo.setText(teacherInfo);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở màn hình AddEditTeacherActivity và truyền dữ liệu hiện có
                Intent intent = new Intent();
                intent.setClass(context, AddEditTeacherActivity.class);
                intent.putExtra("teacher_code", teacher.getCode());
                intent.putExtra("teacher_name", teacher.getName());
                intent.putExtra("teacher_language", teacher.getLanguage());
                intent.putExtra("is_edit", true);
                context.startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc muốn xóa giáo viên " + teacher.getName() + "?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (databaseHelper.deleteTeacher(teacher.getCode())) {
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    teachers.remove(position);
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        return convertView;
    }
}
