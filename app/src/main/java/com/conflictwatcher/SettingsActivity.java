package com.conflictwatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {

    private Button btnLogout;
    private FirebaseAuth auth;

    private EditText userEmail;
    private EditText userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        auth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.settings_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "Logging out!", Toast.LENGTH_LONG).show();
                auth.signOut();
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                finish();
            }
        });

        deleteDialog();


    }

    private void deleteDialog() {

        Button btnDelete = findViewById(R.id.settings_del_account);

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view) {

                //Creating an instance of AlertDialog
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_delete, null);  //Custom layout used for the dialog
                mBuilder.setView(mView);
                TextView textView = mView.findViewById(R.id.dialog_text);  //TextView where privacy text and hyperlink will be displayed

                //Create and dispaly the dialog
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                userEmail = mView.findViewById(R.id.delete_email);
                userPass = mView.findViewById(R.id.delete_password);
                Button close_dialog = mView.findViewById(R.id.confirm_dialog);

                close_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        // Get auth credentials from the user for re-authentication. The example below shows
                        // email and password credentials but there are multiple possible providers,
                        // such as GoogleAuthProvider or FacebookAuthProvider.
                        String credential1 = userEmail.getText().toString();
                        String credential2 = userPass.getText().toString();

                        AuthCredential credential = EmailAuthProvider
                                .getCredential(credential1, credential2);

                        // Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        user.delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("tag", "User Account Successfully deleted.");
                                                            Toast.makeText(SettingsActivity.this, "Account Deleted!", Toast.LENGTH_LONG).show();
                                                            dialog.dismiss();
                                                            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                                                            finish();

                                                        }
                                                        else{
                                                            Log.d("tag", "User Account FAILED to delete.");
                                                            Toast.makeText(SettingsActivity.this, "FAILED To Delete Account!", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });
            }
        });
    }


}
