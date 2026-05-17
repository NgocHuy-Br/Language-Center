package com.example.language_center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddEditStudentActivity extends AppCompatActivity {
    TextView tvTitle;
    EditText edCode, edName, edLevel, edClass;
    Button btnSave, btnCancel;
    DatabaseHelper databaseHelper;
    boolean isEdit = false;
    String originalCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Start Code here
        //Initialize database
        databaseHelper = new DatabaseHelper(this);

        //Mapping
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        edCode = (EditText) findViewById(R.id.edCode);
        edName = (EditText) findViewById(R.id.edName);
        edLevel = (EditText) findViewById(R.id.edLevel);
        edClass = (EditText) findViewById(R.id.edClass);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        //Check if edit mode
        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("is_edit", false);

        if (isEdit) {
            tvTitle.setText("Sửa Học Viên");
            originalCode = intent.getStringExtra("student_code");
            edCode.setText(originalCode);
            edName.setText(intent.getStringExtra("student_name"));
            edLevel.setText(intent.getStringExtra("student_level"));
            edClass.setText(intent.getStringExtra("student_class"));
            edCode.setEnabled(true); 
        } else {
            tvTitle.setText("Thêm Học Viên");
        }

        //Event handle
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edCode.getText().toString().trim();
                String name = edName.getText().toString().trim();
                String level = edLevel.getText().toString().trim();
                String className = edClass.getText().toString().trim();

                if (code.isEmpty() || name.isEmpty() || level.isEmpty() || className.isEmpty()) {
                    Toast.makeText(AddEditStudentActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sửa lỗi: Gọi đúng hàm isValid từ Enum Level nằm trong lớp Student
                if (!Student.Level.isValid(level)) {
                    Toast.makeText(AddEditStudentActivity.this, "Trình độ không hợp lệ! Vui lòng nhập: A1, A2, B1, B2, C1 hoặc C2", Toast.LENGTH_LONG).show();
                    return;
                }

                Student student = new Student(code, name, level.toUpperCase(), className);

                if (isEdit) {
                    if (!code.equals(originalCode)) {
                        if (databaseHelper.checkStudentExists(code)) {
                            Toast.makeText(AddEditStudentActivity.this, "Mã số này đã tồn tại!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (databaseHelper.updateStudent(originalCode, student)) {
                        Toast.makeText(AddEditStudentActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddEditStudentActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (databaseHelper.checkStudentExists(code)) {
                        Toast.makeText(AddEditStudentActivity.this, "Mã số này đã tồn tại!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (databaseHelper.addStudent(student)) {
                        Toast.makeText(AddEditStudentActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddEditStudentActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
