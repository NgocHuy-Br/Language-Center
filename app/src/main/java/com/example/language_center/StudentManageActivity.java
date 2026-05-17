package com.example.language_center;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class StudentManageActivity extends AppCompatActivity {
    ListView listView;
    Button btnAdd, btnGoToTeacher, btnLogout;
    Spinner spinnerLevel;
    EditText etSearch;
    DatabaseHelper databaseHelper;

    ArrayList<Student> allStudentList;
    ArrayList<Student> filteredStudentList;
    StudentAdapter studentAdapter;
    
    ArrayList<String> levelList;
    ArrayAdapter<String> levelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_manage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Start Code here
        databaseHelper = new DatabaseHelper(this);

        //Mapping
        listView = (ListView) findViewById(R.id.listView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnGoToTeacher = (Button) findViewById(R.id.btnGoToTeacher);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);
        etSearch = (EditText) findViewById(R.id.etSearch);

        allStudentList = new ArrayList<>();
        filteredStudentList = new ArrayList<>();
        levelList = new ArrayList<>();

        //Initialize Adapter
        studentAdapter = new StudentAdapter(this, filteredStudentList);
        listView.setAdapter(studentAdapter);

        // Setup Spinner with hardcoded levels from Student.Level enum
        setupSpinner();

        // Load data from DB
        loadData();

        //Event handle
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StudentManageActivity.this, AddEditStudentActivity.class);
                startActivity(intent);
            }
        });

        btnGoToTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StudentManageActivity.this, TeacherManageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StudentManageActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterStudents();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterStudents();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSpinner() {
        levelList.clear();
        levelList.add("Tất cả trình độ");
        // Lấy danh sách trình độ từ Enum trong lớp Student
        for (String level : Student.Level.getStrings()) {
            levelList.add(level);
        }

        levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, levelList);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(levelAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        allStudentList.clear();
        Cursor cursor = databaseHelper.getAllStudents();
        if (cursor.moveToFirst()) {
            do {
                Student s = new Student(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                allStudentList.add(s);
            } while (cursor.moveToNext());
        }
        cursor.close();
        filterStudents();
    }

    private void filterStudents() {
        String selectedLevel = spinnerLevel.getSelectedItem() != null ? spinnerLevel.getSelectedItem().toString() : "Tất cả trình độ";
        String searchText = etSearch.getText().toString().toLowerCase().trim();

        filteredStudentList.clear();

        for (Student student : allStudentList) {
            boolean matchesLevel = selectedLevel.equals("Tất cả trình độ") || student.getLevel().equalsIgnoreCase(selectedLevel);
            boolean matchesSearch = searchText.isEmpty() || 
                                    student.getName().toLowerCase().contains(searchText) || 
                                    student.getClassName().toLowerCase().contains(searchText);

            if (matchesLevel && matchesSearch) {
                filteredStudentList.add(student);
            }
        }

        studentAdapter.notifyDataSetChanged();
    }
}
