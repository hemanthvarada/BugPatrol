package com.example.bugpatrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity1 extends AppCompatActivity {

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
        setContentView(R.layout.activity_signup1);

        name = findViewById(R.id.name1);
        email = findViewById(R.id.email1);
        phone = findViewById(R.id.phone1);
        password = findViewById(R.id.password1);
        signup = findViewById(R.id.btn_signup1);

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
                    Toast.makeText(SignupActivity1.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                HelperClass helperClass = new HelperClass(nam, mail, ph, pass);
                reference.child(mail.replace(".", "_")).setValue(helperClass, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            // There was an error saving the data
                            Toast.makeText(SignupActivity1.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            // Data was saved successfully
                            Toast.makeText(SignupActivity1.this, "Signup successful", Toast.LENGTH_SHORT).show();
                            Intent login = new Intent(SignupActivity1.this, LoginActivity.class);
                            startActivity(login);
                        }
                    }
                });

            }
        });

    }
}