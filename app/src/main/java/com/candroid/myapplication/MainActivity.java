package com.candroid.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private EditText editLock;
   private EditText inputEmail;
private FirebaseAuth mAuth;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences  = getSharedPreferences(getString(R.string.pref_name), MODE_PRIVATE);

Boolean isLog=sharedPreferences.getBoolean("isLog",false);
        setContentView(R.layout.activity_main);

        if(isLog){
            startActivity(new Intent(getApplicationContext(), DashActivity.class));


            finish();

        }
        TextView textReg = findViewById(R.id.textReg);
        Button button = findViewById(R.id.button);
editLock=findViewById(R.id.editLock);
inputEmail=findViewById(R.id.inputEmails);
mAuth=FirebaseAuth.getInstance();


        TextView textPass=findViewById(R.id.textPass);



textPass.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),ForgotActivity.class));
        overridePendingTransition(R.anim.slide_in_r,R.anim.slide_out_l);
    }
});

Notification();

//Login button and firebase code
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String m=editLock.getText().toString();
        String n=inputEmail.getText().toString();
        if(m.isEmpty()||n.isEmpty()){

            Toast.makeText(MainActivity.this,"Please fail",Toast.LENGTH_SHORT).show();
    editLock.setError("wrong");
        inputEmail.setError("wrong2");
        }
    else{

mAuth.signInWithEmailAndPassword(n,m).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user.isEmailVerified()) {
            Shared();
            startActivity(new Intent(getApplicationContext(), DashActivity.class));
finish();
        }
        else{
            user.sendEmailVerification();

            Toast.makeText(MainActivity.this,"please check you verification",Toast.LENGTH_SHORT).show();

        }
        }
    else{
            Toast.makeText(MainActivity.this,"no",Toast.LENGTH_SHORT).show();

        }

    }
});



        }

    }
});
//click listener on register
textReg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_r,R.anim.slide_out_l);
    }
});

    }



    //here we create our notification
    public void Notification(){
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {

        NotificationChannel channel= new NotificationChannel("MyNotification","MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager=getSystemService(NotificationManager.class);
        assert manager != null;
        manager.createNotificationChannel(channel);

    }
    FirebaseMessaging.getInstance().subscribeToTopic("genral")
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    String msg ="Successfull";
                    if (!task.isSuccessful()) {
                        msg ="Failed";
                    }
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });


}

    public  void Shared(){

        sharedPreferences.edit().putBoolean("isLog",true).apply();
    }
}


