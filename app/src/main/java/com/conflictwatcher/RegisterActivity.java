package com.conflictwatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private EditText userName, userEmail, userPw;
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

                if(validation() ==true){

                    String email = userEmail.getText().toString().trim();
                    String password = userPw.getText().toString().trim();

                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                            }
                            else{
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
            }
        });



    }

    private Boolean validation(){
        //Need to add more validation here...
        // 1) user gives 2 passwords which must match
        // 2) user inputs must have a minimum and maximum length
        // 3) user password should be checked for strength(i.e using special chars & Uppercase)

        Boolean result = false;

        String name = userName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPw.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() ){
            Toast toast = Toast.makeText(this, "Make sure all fields are filled in!", Toast.LENGTH_SHORT);
            TextView v = toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.RED);
            toast.show();
        }
        else{
            result = true;
        }

        return result;
    }


    private void createViews(){
        userName = findViewById(R.id.registerUsername);
        userPw = findViewById(R.id.registerPassword);
        userEmail = findViewById(R.id.registerEmail);
        regButton = findViewById(R.id.registerButton);
        goToLogin = findViewById(R.id.registerGoToLogin);

    }

}
