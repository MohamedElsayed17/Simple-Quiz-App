package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;
    Button registerButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);

        initViews();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(email!=null && password!=null && !email.equals("") && !password.equals("")){
                    check_register(email,password);
                }else{
                    Toast.makeText(RegisterActivity.this, "email or password empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    void initViews(){
        emailEditText=(TextInputEditText) findViewById(R.id.register_email_edit_text);
        passwordEditText=(TextInputEditText) findViewById(R.id.register_password_edit_text);
        registerButton = (Button) findViewById(R.id.register_button);
        mAuth = FirebaseAuth.getInstance();
    }

    void check_register(String email,String password){
        final ProgressDialog progressDialog = ProgressDialog.show(this,"","Log in....",true);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())   {
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this, "There is a problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}