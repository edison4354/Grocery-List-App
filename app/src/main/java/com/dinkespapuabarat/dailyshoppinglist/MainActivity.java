package com.dinkespapuabarat.dailyshoppinglist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button btnLogin;
    private TextView signUp;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        mAuth = FirebaseAuth.getInstance();

        //jika sudah pernah login sebelumnya
        if(mAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        mDialog = new ProgressDialog(this);

        email = findViewById(R.id.email_login);
        pass = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btn_login);
        signUp = findViewById(R.id.signup_txt);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPass = pass.getText().toString().trim();

                if(TextUtils.isEmpty(mEmail)){
                    email.setError("Required field...");
                    return ;
                }

                if(TextUtils.isEmpty(mPass)){
                    pass.setError("Required Field..");
                    return;
                }

                mDialog.setMessage("Processing");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(mEmail, mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                });

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));

            }
        });
    }
}
