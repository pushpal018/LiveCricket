package com.pushpal.ipl2021;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Team extends AppCompatActivity implements View.OnClickListener {

    Button csk,dc,kkr,mi,kxip,rr,srh,rcb;

    private AdLoader adLoader;
    private boolean adLoaded = false;
    TemplateView template;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        //action bar set
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Teams");


        //enable back button in action bar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Initializing the Google Admob SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            //Toast.makeText(LiveRadio.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
        }
        });

        //Initializing the AdLoader   objects
        adLoader = new AdLoader.Builder(this, "ca-app-pub-1549251657688500/5795031166").forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template = findViewById(R.id.nativeTemplateView);
                template.setStyles(styles);
                template.setNativeAd(unifiedNativeAd);
                adLoaded = true;
                // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                //Toast.makeText(PointTable.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
            }

        }).build();

        loadNativeAd();
        showNativeAd();

        csk = findViewById(R.id.csk);
        dc = findViewById(R.id.dc);
        kkr = findViewById(R.id.kkr);
        mi = findViewById(R.id.mi);
        rr = findViewById(R.id.rr);
        kxip = findViewById(R.id.kxip);
        srh = findViewById(R.id.srh);
        rcb = findViewById(R.id.rcb);

        csk.setOnClickListener(this);
        dc.setOnClickListener(this);
        kkr.setOnClickListener(this);
        mi.setOnClickListener(this);
        rr.setOnClickListener(this);
        kxip.setOnClickListener(this);
        srh.setOnClickListener(this);
        rcb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.csk){
            Intent intent = new Intent(getApplicationContext(), Players.class);
            intent.putExtra("message", "csk");

            startActivity(intent);
        }
        if(v.getId()==R.id.dc){

            Intent intent = new Intent(getApplicationContext(), Players.class);
            intent.putExtra("message", "dc");

            startActivity(intent);
        }
        if(v.getId()==R.id.kkr){

            Intent intent = new Intent(getApplicationContext(), Players.class);
            intent.putExtra("message", "kkr");

            startActivity(intent);
        }
        if(v.getId()==R.id.mi){

            Intent intent = new Intent(getApplicationContext(), Players.class);
            intent.putExtra("message", "mi");

            startActivity(intent);
        }
        if(v.getId()==R.id.kxip){

            Intent intent = new Intent(getApplicationContext(), Players.class);
            intent.putExtra("message", "kxip");

            startActivity(intent);
        }
        if(v.getId()==R.id.srh){

            Intent intent = new Intent(getApplicationContext(), Players.class);
            intent.putExtra("message", "srh");

            startActivity(intent);
        }
        if(v.getId()==R.id.rcb){

            Intent intent = new Intent(getApplicationContext(), Players.class);
            intent.putExtra("message", "rcb");

            startActivity(intent);
        }
        if(v.getId()==R.id.rr){

            Intent intent = new Intent(getApplicationContext(), Players.class);
            intent.putExtra("message", "rr");

            startActivity(intent);
        }
    }

    private void loadNativeAd() {

        // Creating  an Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();

        // load Native Ad with the Request
        adLoader.loadAd(adRequest);

        // Showing a simple Toast message to user when Native an ad is Loading
        //Toast.makeText(LiveRadio.this, "Native Ad is loading ", Toast.LENGTH_LONG).show();
    }

    private void showNativeAd() {

        if (adLoaded) {
            template.setVisibility(View.VISIBLE);
            // Showing a simple Toast message to user when an Native ad is shown to the user
            //Toast.makeText(LiveRadio.this, "Native Ad  is loaded and Now showing ad  ", Toast.LENGTH_LONG).show();

        }
        else {
            //Load the Native ad if it is not loaded
            loadNativeAd();

            // Showing a simple Toast message to user when Native ad is not loaded
            //Toast.makeText(LiveRadio.this, "Native Ad is not Loaded ", Toast.LENGTH_LONG).show();

        }
    }
    public boolean onSupportNavigateUp() {

        onBackPressed();//go to privious activity on back pressed from actionbar too

        return super.onSupportNavigateUp();
    }
}