package com.pushpal.ipl2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class Feedback extends AppCompatActivity implements View.OnClickListener{

    private EditText editText_name ;
    private EditText editText_feedback;
    private Button sent;
    private Button cancel;

    private boolean mytryagaing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        //action bar set
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Feedback");

        //backprase
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        editText_name = findViewById(R.id.edit1);
        editText_feedback = findViewById(R.id.edit2);
        sent  = findViewById(R.id.sent);
        cancel = findViewById(R.id.clear);

        sent.setOnClickListener(this);
        cancel.setOnClickListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel("mynotification", "myNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(notificationChannel);
        }


        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        String msg = "";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }



                    }
                });
    }


    @Override
    public void onClick(View v) {

        try{

            String name =editText_name.getText().toString();
            String feedback =editText_feedback.getText().toString();



            if(v.getId()==R.id.sent){

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/email");

                intent.putExtra(intent.EXTRA_EMAIL,new String[]{"pushpal.aust@gmail.com"});
                intent.putExtra(intent.EXTRA_SUBJECT,"App feedback");
                intent.putExtra(intent.EXTRA_TEXT,"Name :"+name+" Feedback :"+feedback);

                startActivity(intent.createChooser(intent,"FeedBack with "));


            }if(v.getId()==R.id.clear){



//                Intent intent = new Intent(Feedback.this,MainActivity.class);
//                startActivity(intent);
//
//                if( mInterstitial.isLoaded()){
//                    mInterstitial.show();
//                }

                editText_name.setText("");
                editText_feedback.setText("");



            }


        }catch (Exception e){

            Toast.makeText(Feedback.this,"Please enter your name or feedback",Toast.LENGTH_SHORT).show();

        }


    }

    public boolean onSupportNavigateUp() {

        onBackPressed();//go to privious activity on back pressed from actionbar too

        return super.onSupportNavigateUp();
    }
}