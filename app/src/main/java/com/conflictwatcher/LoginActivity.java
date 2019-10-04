package com.conflictwatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText Email;
    private EditText Password;
    private Button LoginBtn;
    private TextView LoginText;

    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.loginEmail);
        Password = findViewById(R.id.loginPassword);
        LoginBtn = findViewById(R.id.loginButton);
        LoginText = findViewById(R.id.loginText);

        auth = FirebaseAuth.getInstance();

        //Checks if a user has  already logged in to the app
        FirebaseUser user = auth.getCurrentUser();

        progress = new ProgressDialog((this));


        if(user != null){
            startActivity(new Intent(LoginActivity.this, MapActivity.class));
            finish();
        }


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

        progress.setMessage("Please wait...");
        progress.show();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progress.dismiss();
                    emailVerificationCheck();
                }
                else{
                    progress.dismiss();
                    Toast toast = Toast.makeText(LoginActivity.this, "FAILED To Login!", Toast.LENGTH_SHORT);
                    TextView v = toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.RED);
                    toast.show();
                }
            }
        });




    }

    private void emailVerificationCheck(){

        FirebaseUser user = auth.getInstance().getCurrentUser();
        Boolean emailcheck = user.isEmailVerified();

        if(emailcheck){
            Toast.makeText(this, "Logging in...", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MapActivity.class));
            finish();
        }
        else{
            Toast toast = Toast.makeText(this, "You must verify your E-mail address before logging in!", Toast.LENGTH_SHORT);
            TextView v = toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.RED);
            toast.show();

            auth.signOut();
        }



    }

}

