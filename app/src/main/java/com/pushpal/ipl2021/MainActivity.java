package com.pushpal.ipl2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String appPackageName ="";
    private  String strAppLink = "";

    private Button liveTv,liveRadio,team,pointTable,fixture;

    private Boolean mytryagaing = true;
    private AdView mAdView;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for banner ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        prepareAd();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Log.i("hello", "world");
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", " Interstitial not loaded");
                        }
                        prepareAd();
                    }
                });
            }
        }, 10, 1, TimeUnit.HOURS);

        mAdView = findViewById(R.id.adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if(mytryagaing){
                    mAdView.loadAd(new AdRequest.Builder().build());
                    mytryagaing = false;
                }
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

        });

        liveTv = findViewById(R.id.liveTv);
        liveRadio = findViewById(R.id.liveRadio);
        team = findViewById(R.id.team);
        pointTable = findViewById(R.id.pointTable);
        fixture = findViewById(R.id.fixture);

        liveTv.setOnClickListener(this);
        liveRadio.setOnClickListener(this);
        team.setOnClickListener(this);
        fixture.setOnClickListener(this);
        pointTable.setOnClickListener(this);
        //action bar set
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("IPL 2021");


        //for notification........
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
    public void prepareAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1549251657688500/2497756319");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.liveTv){

                Intent intent = new Intent(MainActivity.this,LiveTv.class);
                startActivity(intent);

        }
        if(v.getId()==R.id.liveRadio){

            Intent intent = new Intent(MainActivity.this,LiveRadio.class);
            startActivity(intent);


        }
        if(v.getId()==R.id.team){

            Intent intent = new Intent(MainActivity.this,Team.class);
            startActivity(intent);


        }
        if(v.getId()==R.id.pointTable){

            Intent intent = new Intent(MainActivity.this,PointTable.class);
            startActivity(intent);


        }
        if(v.getId()==R.id.fixture){

            Intent intent = new Intent(MainActivity.this,Fixtures.class);
            startActivity(intent);


        }

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirm exit!!");
        alertDialogBuilder.setIcon(R.drawable.exit);
        alertDialogBuilder.setMessage("Do you want to exit?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"You are clicked on cancel",Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setNeutralButton("Rate this app", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                appPackageName = getApplicationContext().getPackageName();

                try
                {
                    strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
                }
                catch (android.content.ActivityNotFoundException anfe)
                {
                    strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strAppLink));
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    public void onOptionsMenuClosed(Menu menu) {


        super.onOptionsMenuClosed(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.manu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.about){
            Intent intent = new Intent(MainActivity.this,AboutUs.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.rate){

            appPackageName = getApplicationContext().getPackageName();

            try
            {
                strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
            }
            catch (android.content.ActivityNotFoundException anfe)
            {
                strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strAppLink));
            startActivity(intent);
        }
        if(item.getItemId()==R.id.feedback){


            Intent intent = new Intent(MainActivity.this,Feedback.class);
            startActivity(intent);


        }

        if(item.getItemId()==R.id.share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");

            try
            {
                strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
            }
            catch (android.content.ActivityNotFoundException anfe)
            {
                strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
            }

            String subject = "APP NAME/TITLE";
            String body = "Hey! Download by app for free and enjoy IPL 2021 with Live TV." +
                    "\n"+""+strAppLink;

            intent.putExtra(intent.EXTRA_SUBJECT,subject);
            intent.putExtra(intent.EXTRA_TEXT,body);

            startActivity(intent.createChooser(intent,"share with"));
        }

        return super.onOptionsItemSelected(item);
    }
}