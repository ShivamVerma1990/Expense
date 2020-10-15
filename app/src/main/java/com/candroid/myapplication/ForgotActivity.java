package com.candroid.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
  Button button;
        FirebaseAuth mAuth;
           EditText inputEmailFor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
mAuth=FirebaseAuth.getInstance();
    button=findViewById(R.id.button);
    inputEmailFor=findViewById(R.id.inputEmailFor);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String email = inputEmailFor.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(ForgotActivity.this, "please fill all the requirement information", Toast.LENGTH_SHORT).show();
     inputEmailFor.setError("Please provide email");

            inputEmailFor.requestFocus();

            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmailFor.setError("invalid email");
            inputEmailFor.requestFocus();
            return;
        }
        // here we reset our password by using to send Email
mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
            Toast.makeText(ForgotActivity.this, "Check your email please", Toast.LENGTH_SHORT).show();


        }
    else{

            Toast.makeText(ForgotActivity.this, " Try Again!!something wrong", Toast.LENGTH_SHORT).show();


        }

    }
});
    }
});

        }

    @Override




    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_l,R.anim.slide_out_r);}

    }