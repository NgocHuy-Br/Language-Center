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

public class TeacherManagementActivity extends AppCompatActivity {
    ListView listView;
    Button btnAdd, btnGoToStudent, btnLogout;
    DatabaseHelper databaseHelper;

    ArrayList<Teacher> teacherList;
    TeacherAdapter teacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnGoToStudent = findViewById(R.id.btnGoToStudent);
        btnLogout = findViewById(R.id.btnLogout);

        teacherList = new ArrayList<>();
        
        // Setup Adapter
        teacherAdapter = new TeacherAdapter(this, teacherList);
        listView.setAdapter(teacherAdapter);

        loadTeachers();

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherManagementActivity.this, AddEditTeacherActivity.class);
            startActivity(intent);
        });

        btnGoToStudent.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherManagementActivity.this, StudentManagementActivity.class);
            startActivity(intent);
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherManagementActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTeachers();
    }

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
