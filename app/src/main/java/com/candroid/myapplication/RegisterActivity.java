package com.candroid.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {
Toolbar toolbar2;
   EditText inputEmail;
           EditText editLock;
Button button;

    private FirebaseAuth mAuth;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
toolbar2= findViewById(R.id.toolbar2);
inputEmail=findViewById(R.id.inputEmail);
editLock=findViewById(R.id.editLock);
        mAuth = FirebaseAuth.getInstance();

        button=findViewById(R.id.button);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
String Pass=editLock.getText().toString();
        String email=inputEmail.getText().toString();

        if (email.isEmpty()){
            Toast.makeText(RegisterActivity.this,"please fill all the requirment information",Toast.LENGTH_SHORT).show();
            inputEmail.setError("Please fill this email");
return;
        }

if (Pass.isEmpty()){
    Toast.makeText(RegisterActivity.this,"please fill  Password",Toast.LENGTH_SHORT).show();
    editLock.setError("Please fill password");
    return;
}
       mAuth.createUserWithEmailAndPassword(email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {

               if(task.isSuccessful()){
                   startActivity(new Intent(getApplicationContext(),DashActivity.class));

               }
           else{
                   Toast.makeText(RegisterActivity.this,"no",Toast.LENGTH_SHORT).show();


               }

           }
       });

    }
});
setToolbar();
    }
@Override




    public void finish()
{
    super.finish();
    overridePendingTransition(R.anim.slide_in_l,R.anim.slide_out_r);}


//here
public  void setToolbar(){
        setSupportActionBar(toolbar2);
        getSupportActionBar().setTitle("Back");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
}
}
