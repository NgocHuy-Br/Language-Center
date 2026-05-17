package com.example.language_center;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TeacherManageActivity extends AppCompatActivity {
    ListView listView;
    Button btnAdd, btnGoToStudent, btnLogout;
    DatabaseHelper databaseHelper;

    ArrayList<Teacher> teacherList;
    TeacherAdapter teacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_manage);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ view
        listView = (ListView) findViewById(R.id.listView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnGoToStudent = (Button) findViewById(R.id.btnGoToStudent);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        teacherList = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(this, teacherList);
        listView.setAdapter(teacherAdapter);

        loadTeachers();

        // Thêm giáo viên
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TeacherManageActivity.this, AddEditTeacherActivity.class);
                startActivity(intent);
            }
        });

        // Qua trang học viên
        btnGoToStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TeacherManageActivity.this, StudentManageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Thoát
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TeacherManageActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTeachers();
    }

    // Lấy danh sách giáo viên
    private void loadTeachers() {
        teacherList.clear();
        Cursor cursor = databaseHelper.getAllTeachers();
        if (cursor.moveToFirst()) {
            do {
                String code = cursor.getString(0);
                String name = cursor.getString(1);
                String language = cursor.getString(2);
                teacherList.add(new Teacher(code, name, language));
            } while (cursor.moveToNext());
        }
        cursor.close();
        teacherAdapter.notifyDataSetChanged();
    }
}