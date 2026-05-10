package com.example.language_center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edAccount, edPassword;
    Button btLogin;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Mapping
        edAccount = (EditText) findViewById(R.id.editTextAccount);
        edPassword = (EditText) findViewById(R.id.editTextPassword);
        btLogin = (Button) findViewById(R.id.buttonLogin);

        // Event handle
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edAccount.getText().toString();
                String password = edPassword.getText().toString();

                // Check login from SQLite database
                if (databaseHelper.checkLogin(user, password)) {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ManagementActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}