package com.example.bugpatrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView signup;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.signup_renavigate);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {
                } else {
                    checkUser();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent list = new Intent(LoginActivity.this, SignupActivity1.class);
                startActivity(list);
            }
        });
    }

    public Boolean validateUsername() {
        String val = email.getText().toString();
        if (val.isEmpty()) {
            email.setError("Username cannot be empty");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = password.getText().toString();
        if (val.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
    public void checkUser() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        String formattedUserEmail = userEmail.replace(".", "_");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    email.setError(null);
                    boolean passwordMatched = false;
                    String nameFromDB = null;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String passwordFromDB = dataSnapshot.child("password").getValue(String.class);
                        nameFromDB = dataSnapshot.child("name").getValue(String.class);
                        if (passwordFromDB.equals(userPassword)) {
                            passwordMatched = true;
                            break; // Exit the loop once password is matched
                        }
                    }
                    if (passwordMatched) {
                        // Password matched, proceed to MainActivity
                        Intent intent = new Intent(LoginActivity.this, LocationActivity.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", userEmail);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this,"Successfully Logged In",Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Password did not match
                        password.setError("Invalid Credentials");
                        password.requestFocus();
                    }
                } else {
                    // User with provided email does not exist
                    email.setError("User does not exist");
                    email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}