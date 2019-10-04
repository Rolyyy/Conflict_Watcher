package com.conflictwatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button LoginBtn;
    private TextView LoginText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.loginEmail);
        Password = findViewById(R.id.loginPassword);
        LoginBtn = findViewById(R.id.loginButton);
        LoginText = findViewById(R.id.loginText);


        //Called when Login button pressed. Sends user input for validation
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(Email.getText().toString(), Password.getText().toString());
            }
        });

        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    //Checks user e-mail and password
    private void login(String email, String password){
        if(email.equals("test")  && password.equals("123")){
            Toast.makeText(this, "Logging in!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MapActivity.class));
        }
        else{
            Toast.makeText(this, "Incorrect E-mail and/or Password!", Toast.LENGTH_LONG).show();
        }

    }
}
