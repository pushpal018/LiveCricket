package com.pushpal.ipl2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashSceen extends AppCompatActivity {

    MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sceen);

        //action bar hode

        getSupportActionBar().hide();

        //hide title bar

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Thread timer=new Thread()
        {
            @Override
            public void run() {
                try                {
                    music= MediaPlayer.create(SplashSceen.this,R.raw.ipltone);
                    music.start();
                    sleep(3000);

                }
                catch(InterruptedException e)
                {

                }
                finally {
                    Intent i=new Intent(SplashSceen.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        timer.start();

    }

    @Override    protected void onPause() {
        super.onPause();
        music.release();
    }
}