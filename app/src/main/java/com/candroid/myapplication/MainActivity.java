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
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
private Button button;
private TextView textReg;
   private EditText editLock;
   private EditText inputEmail;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
textReg=findViewById(R.id.textReg);
button=findViewById(R.id.button);
editLock=findViewById(R.id.editLock);
inputEmail=findViewById(R.id.inputEmail);
mAuth=FirebaseAuth.getInstance();
Notification();
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
            startActivity(new Intent(getApplicationContext(),DashActivity.class));

        }
    else{
            Toast.makeText(MainActivity.this,"no",Toast.LENGTH_SHORT).show();

        }

    }
});



        }

    }
});
textReg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_r,R.anim.slide_out_l);
    }
});

    }
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
}


