package com.example.language_center;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;

public class StatisticsActivity extends AppCompatActivity {
    RadioGroup radioGroupLevel;
    RadioButton rbBasic, rbIntermediate, rbAdvanced;
    Spinner spinnerClass;
    ListView listView;
    DatabaseHelper databaseHelper;

    ArrayList<Student> studentList;
    StudentAdapter studentAdapter;
    ArrayList<String> classList;
    ArrayAdapter<String> classAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Mapping
        radioGroupLevel = findViewById(R.id.radioGroupLevel);
        rbBasic = findViewById(R.id.rbBasic);
        rbIntermediate = findViewById(R.id.rbIntermediate);
        rbAdvanced = findViewById(R.id.rbAdvanced);
        spinnerClass = findViewById(R.id.spinnerClass);
        listView = findViewById(R.id.listView);

        // Initialize lists
        studentList = new ArrayList<>();
        classList = new ArrayList<>();

        // Default: select Basic level
        rbBasic.setChecked(true);
        loadClassesForLevel("Basic");

        // RadioGroup level change listener
        radioGroupLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String selectedLevel = "";
                if (checkedId == R.id.rbBasic) {
                    selectedLevel = "Basic";
                } else if (checkedId == R.id.rbIntermediate) {
                    selectedLevel = "Intermediate";
                } else if (checkedId == R.id.rbAdvanced) {
                    selectedLevel = "Advanced";
                }
                loadClassesForLevel(selectedLevel);
            }
        });

        // Spinner class selection listener
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLevel = getSelectedLevel();
                String selectedClass = classList.get(position);
                loadStudentsByLevelAndClass(selectedLevel, selectedClass);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private String getSelectedLevel() {
        int checkedId = radioGroupLevel.getCheckedRadioButtonId();
        if (checkedId == R.id.rbBasic) {
            return "Basic";
        } else if (checkedId == R.id.rbIntermediate) {
            return "Intermediate";
        } else if (checkedId == R.id.rbAdvanced) {
            return "Advanced";
        }
        return "";
    }

    private void loadClassesForLevel(String level) {
        classList.clear();
        HashSet<String> uniqueClasses = new HashSet<>();

        Cursor cursor = databaseHelper.getStudentsByLevel(level);
        if (cursor.moveToFirst()) {
            do {
                String className = cursor.getString(3); // class_name is column 3
                uniqueClasses.add(className);
            } while (cursor.moveToNext());
        }
        cursor.close();

        classList.addAll(uniqueClasses);

        if (classList.isEmpty()) {
            classList.add("Không có lớp");
        }

        classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(classAdapter);
    }

    private void loadStudentsByLevelAndClass(String level, String className) {
        studentList.clear();

        if (className.equals("Không có lớp")) {
            studentAdapter = new StudentAdapter(this, studentList);
            listView.setAdapter(studentAdapter);
            return;
        }

        Cursor cursor = databaseHelper.getStudentsByLevelAndClass(level, className);
        if (cursor.moveToFirst()) {
            do {
                String code = cursor.getString(0);
                String name = cursor.getString(1);
                String studentLevel = cursor.getString(2);
                String studentClass = cursor.getString(3);
                studentList.add(new Student(code, name, studentLevel, studentClass));
            } while (cursor.moveToNext());
        }
        cursor.close();

        studentAdapter = new StudentAdapter(this, studentList);
        listView.setAdapter(studentAdapter);
    }
}
