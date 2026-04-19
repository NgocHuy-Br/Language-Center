package com.example.language_center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Welcome extends AppCompatActivity {
    TextView tvWelcome;
    Button btViewStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // mapping
        tvWelcome = (TextView) findViewById(R.id.textViewWelcome);
        btViewStudents = (Button) findViewById(R.id.buttonViewStudents);

        // getIntent
        Intent intent = getIntent();
        String userName = intent.getStringExtra("user_name");

        // setText
        tvWelcome.setText("Xin chào bạn " + userName);

        // Event handle for button
        btViewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentIntent = new Intent(Welcome.this, StudentListActivity.class);
                startActivity(studentIntent);
            }
        });
    }
}