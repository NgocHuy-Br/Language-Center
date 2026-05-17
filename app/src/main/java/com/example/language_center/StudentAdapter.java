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

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context context;
    private ArrayList<Student> students;
    private DatabaseHelper databaseHelper;


    public StudentAdapter(@NonNull Context context, ArrayList<Student> students) {
        super(context, 0, students); // Gọi constructor của lớp cha
        this.context = context;
        this.students = students;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.student_item_layout, parent, false);
        }

        Student student = students.get(position);

        // mapping
        TextView tvStudentInfo = (TextView) convertView.findViewById(R.id.tvStudentInfo);
        Button btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);

        // Đổ dữ liệu vào giao diện
        String studentInfo = "Mã số: " + student.getCode() + "\n" +
                "Họ tên: " + student.getName() + "\n" +
                "Trình độ: " + student.getLevel() + "\n" +
                "Lớp học: " + student.getClassName();
        tvStudentInfo.setText(studentInfo);

        // Event handle nut sua
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang trang AddEditStudentActivity
                Intent intent = new Intent();
                intent.setClass(context, AddEditStudentActivity.class);
                
                // Gửi dữ liệu học viên hiện tại sang trang sửa qua Intent
                intent.putExtra("student_code", student.getCode());
                intent.putExtra("student_name", student.getName());
                intent.putExtra("student_level", student.getLevel());
                intent.putExtra("student_class", student.getClassName());
                intent.putExtra("is_edit", true); // Báo cho trang bên kia biết là đang Sửa chứ không phải Thêm
                
                context.startActivity(intent); // Kích hoạt chuyển trang
            }
        });

        // Event handle xoa
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một hộp thoại xác nhận (AlertDialog) để tránh xóa nhầm
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc muốn xóa học viên " + student.getName() + "?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Gọi hàm deleteStudent trong DatabaseHelper
                                if (databaseHelper.deleteStudent(student.getCode())) {
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    students.remove(position); // Xóa khỏi danh sách đang hiện thị
                                    notifyDataSetChanged(); // Cập nhật lại giao diện ListView ngay lập tức
                                } else {
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Hủy", null) // Không làm gì nếu nhấn Hủy
                        .show();
            }
        });

        return convertView;
    }
}
