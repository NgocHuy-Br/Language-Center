package com.example.language_center;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {
    ListView lvStudent;
    ArrayList<Student> list_students;
    StudentAdapter adapterStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Mapping
        lvStudent = (ListView) findViewById(R.id.listviewStudent);

        // Initialize student list
        list_students = new ArrayList<>();
        Student st1 = new Student("MA1", "Nam", 2000, "Nam", 3.2);
        Student st2 = new Student("MA2", "Lan", 2002, "Nữ", 3.3);
        Student st3 = new Student("MA3", "Hoa", 2004, "Nữ", 2.8);
        Student st4 = new Student("MA4", "Minh", 1998, "Nam", 2.9);
        Student st5 = new Student("MA5", "Phuong", 2005, "Nữ", 3.5);

        list_students.add(st1);
        list_students.add(st2);
        list_students.add(st3);
        list_students.add(st4);
        list_students.add(st5);

        // Custom adapter
        adapterStudent = new StudentAdapter(
                this,
                list_students
        );

        // Set adapter
        lvStudent.setAdapter(adapterStudent);

        // Event handle
        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student selectedStudent = list_students.get(position);
                String message = "You click on: " + selectedStudent.getName();
                Toast.makeText(StudentListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
