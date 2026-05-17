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

public class AddEditTeacherActivity extends AppCompatActivity {
    TextView tvTitle;
    EditText edCode, edName, edLanguage;
    Button btnSave, btnCancel;
    DatabaseHelper databaseHelper;
    boolean isEdit = false;
    String originalCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_teacher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Mapping
        tvTitle = findViewById(R.id.tvTitle);
        edCode = findViewById(R.id.edCode);
        edName = findViewById(R.id.edName);
        edLanguage = findViewById(R.id.edLanguage);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Check if edit mode
        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("is_edit", false);

        if (isEdit) {
            tvTitle.setText("Sửa Giáo Viên");
            originalCode = intent.getStringExtra("teacher_code");
            edCode.setText(originalCode);
            edName.setText(intent.getStringExtra("teacher_name"));
            edLanguage.setText(intent.getStringExtra("teacher_language"));
            // Now allowing editing the code as per user request
            edCode.setEnabled(true);
        } else {
            tvTitle.setText("Thêm Giáo Viên");
        }

        // Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edCode.getText().toString().trim();
                String name = edName.getText().toString().trim();
                String language = edLanguage.getText().toString().trim();

                if (code.isEmpty() || name.isEmpty() || language.isEmpty()) {
                    Toast.makeText(AddEditTeacherActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                Teacher teacher = new Teacher(code, name, language);

                if (isEdit) {
                    // If code is changed, check if the new code already exists
                    if (!code.equals(originalCode)) {
                        if (databaseHelper.checkTeacherExists(code)) {
                            Toast.makeText(AddEditTeacherActivity.this, "Mã số này đã tồn tại!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    if (databaseHelper.updateTeacher(originalCode, teacher)) {
                        Toast.makeText(AddEditTeacherActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddEditTeacherActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Check if code exists before adding
                    if (databaseHelper.checkTeacherExists(code)) {
                        Toast.makeText(AddEditTeacherActivity.this, "Mã số này đã tồn tại!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (databaseHelper.addTeacher(teacher)) {
                        Toast.makeText(AddEditTeacherActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddEditTeacherActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
