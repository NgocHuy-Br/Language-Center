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
import java.util.HashSet;
import java.util.Set;

public class StudentManagementActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_student_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        // Mapping
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnGoToTeacher = findViewById(R.id.btnGoToTeacher);
        btnLogout = findViewById(R.id.btnLogout);
        spinnerLevel = findViewById(R.id.spinnerLevel);
        etSearch = findViewById(R.id.etSearch);

        allStudentList = new ArrayList<>();
        filteredStudentList = new ArrayList<>();
        levelList = new ArrayList<>();

        // Initialize Adapter
        studentAdapter = new StudentAdapter(this, filteredStudentList);
        listView.setAdapter(studentAdapter);

        // Load data
        loadData();

        // Listeners
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(StudentManagementActivity.this, AddEditStudentActivity.class);
            startActivity(intent);
        });

        btnGoToTeacher.setOnClickListener(v -> {
            Intent intent = new Intent(StudentManagementActivity.this, TeacherManagementActivity.class);
            startActivity(intent);
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(StudentManagementActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        // 1. Load all students
        allStudentList.clear();
        Cursor cursor = databaseHelper.getAllStudents();
        Set<String> uniqueLevels = new HashSet<>();
        uniqueLevels.add("Tất cả trình độ");

        if (cursor.moveToFirst()) {
            do {
                Student s = new Student(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                allStudentList.add(s);
                uniqueLevels.add(s.getLevel());
            } while (cursor.moveToNext());
        }
        cursor.close();

        // 2. Update Spinner
        String currentSelection = spinnerLevel.getSelectedItem() != null ? spinnerLevel.getSelectedItem().toString() : "Tất cả trình độ";
        levelList.clear();
        levelList.addAll(uniqueLevels);
        
        levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, levelList);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(levelAdapter);

        // Restore selection if exists
        int pos = levelList.indexOf(currentSelection);
        spinnerLevel.setSelection(pos != -1 ? pos : 0);

        filterStudents();
    }

    private void filterStudents() {
        String selectedLevel = spinnerLevel.getSelectedItem() != null ? spinnerLevel.getSelectedItem().toString() : "Tất cả trình độ";
        String searchText = etSearch.getText().toString().toLowerCase().trim();

        filteredStudentList.clear();

        for (Student student : allStudentList) {
            boolean matchesLevel = selectedLevel.equals("Tất cả trình độ") || student.getLevel().equals(selectedLevel);
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
