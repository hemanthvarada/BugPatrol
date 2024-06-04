package com.example.bugpatrol;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button login;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.btn_login_renavigate);
        signup = findViewById(R.id.signup_renavigate);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start SignupActivity
                Intent intent = new Intent(MainActivity.this, SignupActivity1.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_page = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login_page);
            }
        });
    }
}
