package com.candroid.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {
Toolbar toolbar2;
   EditText inputEmail;
           EditText editLock;
Button button;
ProgressBar progressBar;
 EditText etFull;
 EditText editAge;
    private FirebaseAuth mAuth;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
toolbar2= findViewById(R.id.toolbar2);
inputEmail=findViewById(R.id.inputEmails);
editLock=findViewById(R.id.editLock);
        mAuth = FirebaseAuth.getInstance();
//progressBar=findViewById(R.id.progressBar);
        editAge=findViewById(R.id.editAge);
        etFull=findViewById(R.id.etFull);

        button=findViewById(R.id.button);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
final String Pass=editLock.getText().toString().trim();
        final String email=inputEmail.getText().toString().trim();

final String age=editAge.getText().toString().trim();
final String name=etFull.getText().toString().trim();
        if (email.isEmpty()){
            Toast.makeText(RegisterActivity.this,"please fill all the requirment information",Toast.LENGTH_SHORT).show();
            inputEmail.setError("Please fill this email");

            inputEmail.requestFocus();

            return;
        }
if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
    inputEmail.setError("Incorrect email");
inputEmail.requestFocus();
    return;
}

     if(age.isEmpty()){


         editAge.setError("Wrong");
     editAge.requestFocus();
     return;

     }
     if(name.isEmpty()){

         etFull.setError("Wrong Name");
     etFull.requestFocus();
     return;

     }
        if (Pass.isEmpty()){
            Toast.makeText(RegisterActivity.this,"please fill  Password",Toast.LENGTH_SHORT).show();
            editLock.setError("Please fill password");
            editLock.requestFocus();

            return;
        }
        if(Pass.length()<6)
        {

            editLock.setError("please your password length minimum 6");
            editLock.requestFocus()
            ;
            return;
        }

//progressBar.setVisibility(View.VISIBLE);
       mAuth.createUserWithEmailAndPassword(email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {

               if(task.isSuccessful()){
User user=new User(email,age,name);
FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
   if(task.isSuccessful()){
       Toast.makeText(RegisterActivity.this,"yes",Toast.LENGTH_SHORT).show();
  // progressBar.setVisibility(View.GONE);
   }
   else{
       Toast.makeText(RegisterActivity.this,"no",Toast.LENGTH_SHORT).show();

   }

    }
});

               }
           else{
                   Toast.makeText(RegisterActivity.this,"noo",Toast.LENGTH_SHORT).show();


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
