package com.conflictwatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText userName, userEmail, userPw, userPw2;
    private Button regButton;
    private TextView goToLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createViews();

        auth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validation()){

                    String email = userEmail.getText().toString().trim();
                    String password = userPw.getText().toString().trim();

                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                emailVerification();

                            }
                            else{

                                //NEED TO:
                                //Get reason for failure from Firebase server(part of API)

                                Toast.makeText(RegisterActivity.this, "FAILED To Register!", Toast.LENGTH_LONG).show();



                            }
                        }
                    });
                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });



    }

    private Boolean validation(){
        // 1) user gives 2 passwords which must match
        // 2) user inputs must have a minimum and maximum length
        // 3) user password should be checked for strength(i.e using special chars & Uppercase)

        boolean result = true;

        String name = userName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPw.getText().toString();
        String password2 = userPw2.getText().toString();

        if(name.isEmpty()) {
            userName.setError("Please don't leave blank!");
            result = false;
        }

        if(email.isEmpty()) {
            userEmail.setError("Please don't leave blank!");
            result = false;
        }

        if(password.isEmpty()) {
            userPw.setError("Please don't leave blank!");
            result = false;
        }

        if(password2.isEmpty()) {
            userPw2.setError("Please don't leave blank!");
            result = false;

        }

        if(!password.equals(password2)) {
            userPw.setError("Your passwords do not match!");
            userPw2.setError("Your passwords do not match!");

            result = false;
        }

        if(password.length()<6) {
            userPw.setError("Password must be at least 6 characters long!");
            userPw2.setError("Password must be at least 6 characters long");
            result = false;

        }


        return result;
    }

    private void emailVerification(){

        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registration Successful. E-mail Verification has been sent!", Toast.LENGTH_LONG).show();
                        auth.signOut();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                    else{
                        Toast toast = Toast.makeText(RegisterActivity.this, "FAILED to send verification e-mail!", Toast.LENGTH_SHORT);
                        TextView v = toast.getView().findViewById(android.R.id.message);
                        v.setTextColor(Color.RED);
                        toast.show();
                    }
                }
            });
        }

    }


    private void createViews(){
        userName = findViewById(R.id.registerUsername);
        userPw = findViewById(R.id.registerPassword);
        userPw2 = findViewById(R.id.registerPassword2);
        userEmail = findViewById(R.id.registerEmail);
        regButton = findViewById(R.id.registerButton);
        goToLogin = findViewById(R.id.registerGoToLogin);


    }

}
