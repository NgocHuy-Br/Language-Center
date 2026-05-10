package com.example.language_center;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ManagementActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton rbStudent, rbTeacher;
    ListView listView;
    Button btnAdd, btnStatistics;
    DatabaseHelper databaseHelper;

    ArrayList<Student> studentList;
    ArrayList<Teacher> teacherList;
    StudentAdapter studentAdapter;
    TeacherAdapter teacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Mapping
        radioGroup = findViewById(R.id.radioGroup);
        rbStudent = findViewById(R.id.rbStudent);
        rbTeacher = findViewById(R.id.rbTeacher);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnStatistics = findViewById(R.id.btnStatistics);

        // Initialize lists
        studentList = new ArrayList<>();
        teacherList = new ArrayList<>();

        // Default: show students
        rbStudent.setChecked(true);
        loadStudents();

        // RadioGroup change listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbStudent) {
                    loadStudents();
                } else if (checkedId == R.id.rbTeacher) {
                    loadTeachers();
                }
            }
        });

        // Add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbStudent.isChecked()) {
                    Intent intent = new Intent(ManagementActivity.this, AddEditStudentActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ManagementActivity.this, AddEditTeacherActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Statistics button
        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        // ListView item click for edit
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (rbStudent.isChecked()) {
                    Student student = studentList.get(position);
                    Intent intent = new Intent(ManagementActivity.this, AddEditStudentActivity.class);
                    intent.putExtra("student_code", student.getCode());
                    intent.putExtra("student_name", student.getName());
                    intent.putExtra("student_level", student.getLevel());
                    intent.putExtra("student_class", student.getClassName());
                    intent.putExtra("is_edit", true);
                    startActivity(intent);
                } else {
                    Teacher teacher = teacherList.get(position);
                    Intent intent = new Intent(ManagementActivity.this, AddEditTeacherActivity.class);
                    intent.putExtra("teacher_code", teacher.getCode());
                    intent.putExtra("teacher_name", teacher.getName());
                    intent.putExtra("teacher_language", teacher.getLanguage());
                    intent.putExtra("is_edit", true);
                    startActivity(intent);
                }
            }
        });

        // ListView long click for delete
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (rbStudent.isChecked()) {
                    final Student student = studentList.get(position);
                    new AlertDialog.Builder(ManagementActivity.this)
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc muốn xóa học viên " + student.getName() + "?")
                            .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (databaseHelper.deleteStudent(student.getCode())) {
                                        Toast.makeText(ManagementActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        loadStudents();
                                    } else {
                                        Toast.makeText(ManagementActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                } else {
                    final Teacher teacher = teacherList.get(position);
                    new AlertDialog.Builder(ManagementActivity.this)
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc muốn xóa giáo viên " + teacher.getName() + "?")
                            .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (databaseHelper.deleteTeacher(teacher.getCode())) {
                                        Toast.makeText(ManagementActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        loadTeachers();
                                    } else {
                                        Toast.makeText(ManagementActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload data when returning from add/edit screen
        if (rbStudent.isChecked()) {
            loadStudents();
        } else {
            loadTeachers();
        }
    }

    private void loadStudents() {
        studentList.clear();
        Cursor cursor = databaseHelper.getAllStudents();
        if (cursor.moveToFirst()) {
            do {
                String code = cursor.getString(0);
                String name = cursor.getString(1);
                String level = cursor.getString(2);
                String className = cursor.getString(3);
                studentList.add(new Student(code, name, level, className));
            } while (cursor.moveToNext());
        }
        cursor.close();

        studentAdapter = new StudentAdapter(this, studentList);
        listView.setAdapter(studentAdapter);
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

        teacherAdapter = new TeacherAdapter(this, teacherList);
        listView.setAdapter(teacherAdapter);
    }
}
