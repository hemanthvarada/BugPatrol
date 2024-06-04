package com.example.bugpatrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    Button signup;

    EditText name;
    EditText email;
    EditText phone;
    EditText password;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String nam = name.getText().toString();
                String mail = email.getText().toString();
                String ph = phone.getText().toString();
                String pass = password.getText().toString();

                if (nam.isEmpty() || mail.isEmpty() || ph.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                HelperClass helperClass = new HelperClass(nam, mail, ph, pass);
                reference.child(mail.replace(".", "_")).setValue(helperClass);

                Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }
}